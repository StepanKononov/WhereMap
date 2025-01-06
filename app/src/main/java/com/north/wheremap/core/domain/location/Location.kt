package com.north.wheremap.core.domain.location

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double,
    val long: Double,
)