package com.north.wheremap.map.ui

data class PointUI(
    val id: String,
    val collectionId: String,
    val latitude: Double,
    val longitude: Double,
    val name: String = "",
    val description: String = ""
)