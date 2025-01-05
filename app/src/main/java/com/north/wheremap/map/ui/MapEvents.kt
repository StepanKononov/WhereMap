package com.north.wheremap.map.ui

import com.north.wheremap.map.location.Location

sealed interface MapEvents {

    data class ZoomToPoint(val point: Location) : MapEvents
}