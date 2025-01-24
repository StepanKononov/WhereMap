package com.north.wheremap.map.ui

import com.north.wheremap.core.domain.point.Point

fun Point.toUi(): PointUI {
    return PointUI(
        id = id ?: throw IllegalStateException("WTF get null point id from domain layer"),
        collectionId = collectionId,
        latitude = latitude,
        longitude = longitude,
        name = name ?: "%.4f - %.4f".format(latitude, longitude),
        description = description ?: "",
    )
}