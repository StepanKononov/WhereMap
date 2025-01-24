package com.north.wheremap.core.data.network.collection

import com.north.wheremap.core.domain.collection.Collection

fun CollectionDto.toCollection(): Collection {
    return Collection(
        id = this.id,
        name = this.name,
        description = this.description,
        city = this.city,
        isPrivate = this.isPrivate,
    )
}