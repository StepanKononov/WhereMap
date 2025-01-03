package com.north.wheremap.map.location

import android.location.Location
import com.mapbox.geojson.Point

fun Location.toDomainLocation(): com.north.wheremap.map.location.Location {
    return com.north.wheremap.map.location.Location(
        lat = latitude,
        long = longitude
    )
}

fun Point.toLocation() = Location(lat = latitude(), long = longitude())