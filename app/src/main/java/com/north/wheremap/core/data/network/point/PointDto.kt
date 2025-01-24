package com.north.wheremap.core.data.network.point

import kotlinx.serialization.Serializable

@Serializable
data class PointDto (
    val id: String,
    val collectionId: String,
    val latitude: Double,
    val longitude: Double,
    val name: String?,
    val description: String?
)