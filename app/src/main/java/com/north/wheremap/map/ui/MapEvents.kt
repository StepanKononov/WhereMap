package com.north.wheremap.map.ui

import com.north.wheremap.core.domain.location.Location

sealed interface MapEvents {

    data class ZoomToPoint(val point: Location) : MapEvents
}