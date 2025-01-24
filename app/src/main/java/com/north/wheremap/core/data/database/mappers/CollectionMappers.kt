package com.north.wheremap.core.data.database.mappers

import com.north.wheremap.core.data.database.entity.CollectionEntity
import com.north.wheremap.core.domain.collection.Collection
import org.bson.types.ObjectId

fun CollectionEntity.toDomain() = Collection(
    id = this.id,
    name = this.name,
    // Todo: notnull нужно было для работы с сервером, null отбивает 400. Нормальный запрос GetCollectionRequest решит проблему но мне лень
    description = this.description ?: "",
    city = this.city ?: "",
    isPrivate = this.isPrivate
)

fun Collection.toEntity() = CollectionEntity(
    id = id ?: ObjectId().toHexString(),
    name = this.name,
    description = this.description,
    city = this.city,
    isPrivate = this.isPrivate
)
