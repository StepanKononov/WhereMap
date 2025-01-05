package com.north.wheremap.map.location

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val long: Double
)