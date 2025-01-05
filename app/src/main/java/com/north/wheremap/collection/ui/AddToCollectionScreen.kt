package com.north.wheremap.collection.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.GenericStyle

@Composable
fun AddToCollectionRoot(viewModel: AddToCollectionViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    AddToCollectionScreen(
        screenState = state.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToCollectionScreen(
    screenState: AddToCollectionScreenState,
    onConfirm: (String) -> Unit = {},
) {
    val center = Point.fromLngLat(screenState.point.long, screenState.point.lat)
    val mapViewportState =
        rememberMapViewportState {
            setCameraOptions {
                center(center)
                zoom(12.0)
                pitch(0.0)
                bearing(0.0)
            }
        }


    val mapState = rememberMapState {
        gesturesSettings = gesturesSettings
            .toBuilder()
            .setScrollEnabled(false)
            .setPitchEnabled(false)
            .setRotateEnabled(false)
            .setDoubleTapToZoomInEnabled(false)
            .setQuickZoomEnabled(false)
            .setRotateEnabled(false)
            .setPinchScrollEnabled(false)
            .setPinchToZoomEnabled(false)
            .setSimultaneousRotateAndPinchToZoomEnabled(false)
            .build()
    }
    var pointName by remember { mutableStateOf("") }
    val pointColor = MaterialTheme.colorScheme.tertiary
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Добавить в коллекцию") },
                actions = {
                    TextButton(onClick = {
                        onConfirm(pointName)
                    }) {
                        Text("Готово")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            item {
                MapboxMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                        .clip(RoundedCornerShape(40)),
                    mapViewportState = mapViewportState,
                    scaleBar = {},
                    logo = {},
                    attribution = {},
                    style = {
                        GenericStyle(style = if (isSystemInDarkTheme()) Style.DARK else Style.LIGHT)
                    },
                    compass = {
                        Compass(
                            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
                        )
                    },
                    mapState = mapState
                ) {
                    CircleAnnotation(center) {
                        circleRadius = 8.0
                        circleColor = pointColor
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
            item {
                OutlinedTextField(
                    value = pointName,
                    onValueChange = { pointName = it },
                    label = { Text("Название точки") },
                    placeholder = { Text("Введите название") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}