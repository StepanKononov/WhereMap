package com.north.wheremap.map.location

import android.location.Location
import com.mapbox.geojson.Point

fun Location.toDomainLocation(): com.north.wheremap.core.domain.location.Location {
    return com.north.wheremap.core.domain.location.Location(
        lat = latitude,
        long = longitude
    )
}

fun Point.toLocation() =
    com.north.wheremap.core.domain.location.Location(lat = latitude(), long = longitude())