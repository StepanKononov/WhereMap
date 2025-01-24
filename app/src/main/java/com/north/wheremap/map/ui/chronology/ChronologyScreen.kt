package com.north.wheremap.map.ui.chronology

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.north.wheremap.R
import com.north.wheremap.map.ui.PointUI

@Composable
fun ChronologyScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ChronologyViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mapViewportState = rememberMapViewportState()

    LaunchedEffect(state.currentPoint) {
        state.currentPoint?.let { point ->
            mapViewportState.flyToLocation(point.latitude, point.longitude)
        }
    }

    ChronologyScreen(
        modifier = modifier,
        state = state,
        mapViewportState = mapViewportState,
        onPrevious = { viewModel.onAction(ChronologyActions.MoveToPreviousPoint) },
        onNext = { viewModel.onAction(ChronologyActions.MoveToNextPoint) },
        onBackPressed = onBackPressed
    )
}

@Composable
fun ChronologyScreen(
    modifier: Modifier = Modifier,
    state: ChronologyScreenState,
    mapViewportState: MapViewportState,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        ChronologyMapView(
            points = state.points,
            currentPoint = state.currentPoint,
            mapViewportState = mapViewportState,
            modifier = Modifier.fillMaxSize()
        )
        BackButton(
            onBack = onBackPressed,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
                .padding(16.dp)
        )
        if (state.currentPoint != null) {
            ChronologyBottomCard(
                currentPoint = state.currentPoint,
                onPrevious = onPrevious,
                onNext = onNext,
                enabledPrevious = state.points.indexOf(state.currentPoint) > 0,
                enabledNext = state.currentPoint != state.points.lastOrNull(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ChronologyMapView(
    points: List<PointUI>,
    currentPoint: PointUI?,
    mapViewportState: MapViewportState,
    modifier: Modifier = Modifier
) {
    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        scaleBar = {},
        logo = {},
        attribution = {},
        style = {
            GenericStyle(if (isSystemInDarkTheme()) Style.DARK else Style.LIGHT)
        },
        compass = {
            Compass(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
            )
        }
    ) {
        points.forEach { point ->
            point.renderLocationMarker(
                color = if (point == currentPoint) MaterialTheme.colorScheme.tertiary
                else MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ChronologyBottomCard(
    currentPoint: PointUI,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    enabledPrevious: Boolean,
    enabledNext: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onPrevious,
                enabled = enabledPrevious
            ) {
                Text(text = stringResource(R.string.go_previous_point))
            }

            Text(
                text = currentPoint.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onNext,
                enabled = enabledNext
            ) {
                Text(text = stringResource(R.string.go_next_point))
            }
        }
    }
}

@Composable
fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            .clickable(onClick = onBack),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
@Suppress("ComposableNaming")
private fun PointUI.renderLocationMarker(color: Color) {
    CircleAnnotation(point = Point.fromLngLat(this.longitude, this.latitude)) {
        circleRadius = 8.0
        circleColor = color
    }
}

private fun MapViewportState.flyToLocation(lat: Double, lng: Double) {
    this.flyTo(
        CameraOptions.Builder()
            .center(Point.fromLngLat(lng, lat))
            .zoom(12.0)
            .pitch(0.0)
            .bearing(0.0)
            .build(),
        animationOptions = MapAnimationOptions.mapAnimationOptions {
            duration(1_500)
        }
    )
}