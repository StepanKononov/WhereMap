package com.north.wheremap.core.domain.point

import kotlinx.serialization.Serializable

@Serializable // TODO: вообще это тут лишнее, но писать отдельные PointRequest мне лень
data class Point(
    val id: String? = null,  // null for new point
    val collectionId: String,
    val latitude: Double,
    val longitude: Double,
    val name: String?,
    val description: String?,
)