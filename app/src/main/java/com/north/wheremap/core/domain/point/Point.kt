package com.north.wheremap.core.domain.point

data class Point(
    val id: String? = null,  // null for new point
    val collectionId: String,
    val latitude: Double,
    val longitude: Double,
    val name: String?,
    val description: String?,
)