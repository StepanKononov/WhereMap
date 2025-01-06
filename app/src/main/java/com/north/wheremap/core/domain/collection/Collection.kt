package com.north.wheremap.core.domain.collection

data class Collection(
    val id: String? = null, // null for new collection
    val name: String,
    val description: String? = null,
    val city: String? = null,
    val isPrivate: Boolean
)