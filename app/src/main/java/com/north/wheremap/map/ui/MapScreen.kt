@file:OptIn(ExperimentalPermissionsApi::class)

package com.north.wheremap.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.north.wheremap.R
import com.north.wheremap.core.navigation.AddToCollectionRoute
import com.north.wheremap.core.ui.BaseConfirmDialog
import com.north.wheremap.core.ui.ObserveAsEvents
import com.north.wheremap.map.location.Location
import com.north.wheremap.map.location.toLocation

@Composable
fun MapScreenRoot(
    onAddNewPoint: (AddToCollectionRoute) -> Unit,
) {
    MapScreen(
        onAddNewPoint
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    onAddNewPoint: (AddToCollectionRoute) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()
    val selectedPoint by viewModel.selectedPoint.collectAsStateWithLifecycle()
    val mapViewportState = rememberMapViewportState()
    val context = LocalContext.current

    val locationPermissionsState = rememberPermissionsState(viewModel)

    val openAlertDialog = remember { mutableStateOf(false) }
    if (openAlertDialog.value) {
        PermissionDialog(context, openAlertDialog)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is MapEvents.ZoomToPoint -> {
                mapViewportState.flyToLocation(event.point)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            val hasSelectedPoint = selectedPoint != null
            AnimatedContent(
                targetState = hasSelectedPoint,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut() using SizeTransform()
                }, label = "23"
            ) { targetState ->
                if (targetState) {
                    Row {
                        ExtendedFloatingActionButton(
                            onClick = {
                                viewModel.onAction(MapScreenActions.ResetSelectedPoint)
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            },
                            text = { Text(text = "Отменить") },
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(Modifier.size(32.dp))
                        selectedPoint?.let {
                            ExtendedFloatingActionButton(
                                onClick = {
                                    onAddNewPoint(AddToCollectionRoute(it))
                                    viewModel.onAction(MapScreenActions.ResetSelectedPoint)
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null
                                    )
                                },
                                text = { Text(text = "Добавить") }
                            )
                        }
                    }

                } else {
                    LocationFloatingActionButton(
                        permissionsState = locationPermissionsState,
                        openAlertDialog = openAlertDialog,
                        currentLocation = currentLocation,
                        mapViewportState = mapViewportState
                    )
                }
            }
        }
    ) {
        MapView(
            viewModel = viewModel,
            currentLocation = currentLocation,
            mapViewportState = mapViewportState,
            selectedPoint = selectedPoint,
            modifier = modifier
        )
    }
}

@Composable
private fun MapView(
    viewModel: MapViewModel,
    currentLocation: Location?,
    mapViewportState: MapViewportState,
    modifier: Modifier,
    selectedPoint: Location?
) {
    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        scaleBar = {},
        logo = {},
        attribution = {},
        onMapLongClickListener = { point ->
            viewModel.onAction(MapScreenActions.SetNewPoint(point.toLocation()))
            true
        },
        style = {
            GenericStyle(style = if (isSystemInDarkTheme()) Style.DARK else Style.LIGHT)
        },
        compass = {
            Compass(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
            )
        }
    ) {
        // TODO: Мы можем открывать карту сразу с нужной точки? (сразу на позиции пользователя)
        currentLocation?.renderLocationMarker(
            color = MaterialTheme.colorScheme.primary
        )
        selectedPoint?.renderLocationMarker(
            color = MaterialTheme.colorScheme.tertiary
        )
/*
        MapEffect(key1 = Unit) { mapView ->
            val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
                mapViewportState.setCameraOptions(CameraOptions.Builder().bearing(it).build())
            }

            val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
                mapViewportState.setCameraOptions(CameraOptions.Builder().center(it).build())
                mapView.gestures.focalPoint = mapView.mapboxMap.pixelForCoordinate(it)
            }
            mapView.location.apply {
                enabled = true
                addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
                addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            }
        }

 */
    }


}

@Composable
private fun PermissionDialog(
    context: Context,
    openAlertDialog: MutableState<Boolean>
) {
    BaseConfirmDialog(
        onConfirmation = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
            openAlertDialog.value = false
        },
        onDismissRequest = { openAlertDialog.value = false },
        dialogTitle = stringResource(R.string.location_permission_dialog_title),
        dialogText = stringResource(R.string.location_permission_dialog_text),
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun LocationFloatingActionButton(
    permissionsState: MultiplePermissionsState,
    openAlertDialog: MutableState<Boolean>,
    currentLocation: Location?,
    mapViewportState: MapViewportState,
) {
    SmallFloatingActionButton(
        onClick = {
            when {
                permissionsState.allPermissionsGranted -> {
                    currentLocation?.let {
                        mapViewportState.flyToLocation(it)
                    }
                }

                permissionsState.shouldShowRationale -> {
                    permissionsState.launchMultiplePermissionRequest()
                }

                else -> {

                    openAlertDialog.value = true
                }
            }
        },
        shape = CircleShape,
    ) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun rememberPermissionsState(viewModel: MapViewModel): MultiplePermissionsState {
    return rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) { permissions ->
        val hasCoarsePermission = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFinePermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

        val accepted = hasCoarsePermission && hasFinePermission
        viewModel.onAction(MapScreenActions.SubmitLocationPermission(accepted))
    }.apply {
        LaunchedEffect(allPermissionsGranted) {
            viewModel.onAction(MapScreenActions.SubmitLocationPermission(allPermissionsGranted))
        }
    }
}

@Composable
private fun Location.renderLocationMarker(
    color: Color
) {
    CircleAnnotation(point = Point.fromLngLat(this.long, this.lat)) {
        circleRadius = 8.0
        circleColor = color
    }
}

private fun MapViewportState.flyToLocation(location: Location) {
    this.flyTo(
        CameraOptions.Builder()
            .center(Point.fromLngLat(location.long, location.lat))
            .zoom(12.0)
            .pitch(0.0)
            .bearing(0.0)
            .build(),
        animationOptions = MapAnimationOptions.mapAnimationOptions {
            duration(1_000)
        }
    )
}
