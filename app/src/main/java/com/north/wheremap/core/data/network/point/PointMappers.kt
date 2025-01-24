package com.north.wheremap.core.data.network.point

import com.north.wheremap.core.domain.point.Point

fun PointDto.toPoint(): Point = Point(
    id = this.id,
    collectionId = this.collectionId,
    latitude = this.latitude,
    longitude = this.longitude,
    name = this.name,
    description = this.description
)