package com.north.wheremap.map.ui

import com.north.wheremap.core.domain.location.Location

sealed interface MapScreenActions {
    data class SetNewPoint(
        val location: Location
    ) : MapScreenActions

    data object ResetSelectedPoint : MapScreenActions

    data class SubmitLocationPermission(
        val acceptedLocationPermission: Boolean,
    ) : MapScreenActions
}