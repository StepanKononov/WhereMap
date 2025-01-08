package com.north.wheremap.core.data.database.mappers

import com.north.wheremap.core.data.database.entity.PointEntity
import com.north.wheremap.core.domain.point.Point
import org.bson.types.ObjectId

fun PointEntity.toDomain() = Point(
    id = this.id,
    collectionId = this.collectionId,
    latitude = this.latitude,
    longitude = this.longitude,
    name = this.name,
    description = this.description,
)

fun Point.toEntity() = PointEntity(
    id = id ?: ObjectId().toHexString(),
    collectionId = this.collectionId,
    latitude = this.latitude,
    longitude = this.longitude,
    name = this.name,
    description = this.description,
)
