package com.north.wheremap.map.ui

import com.north.wheremap.map.location.Location

sealed interface MapScreenActions {
    data class SetNewPoint(
        val location: Location
    ) : MapScreenActions

    data class SubmitLocationPermission(
        val acceptedLocationPermission: Boolean,
    ) : MapScreenActions
}