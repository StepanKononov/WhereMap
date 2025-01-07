package com.north.wheremap.core.domain.collection


// TODO: add last edited time (revision) to sync with other platforms
data class Collection(
    val id: String? = null, // null for new collection
    val name: String,
    val description: String? = null,
    val city: String? = null,
    val isPrivate: Boolean
)