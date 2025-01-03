package com.north.wheremap.map.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
) {
    // TODO: основная карта. Нужно отображать местоположение пользователя. Лонгтап -> создание точки

    val mapViewportState = rememberMapViewportState()
    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        scaleBar = {},
        logo = {},
        attribution = {},
        style = { MapboxStandardStyle() }
    ) {}
}