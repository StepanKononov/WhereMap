package com.north.wheremap.core.data.network.collection

import kotlinx.serialization.Serializable

@Serializable
data class CollectionDto(
    val id: String,
    val name: String,
    val description: String,
    val city: String,
    val isPrivate: Boolean,
)
