package com.north.wheremap.core.domain.collection

import kotlinx.serialization.Serializable


// TODO: add last edited time (revision) to sync with other platforms
@Serializable
data class Collection(
    val id: String? = null, // null for new collection
    val name: String,
    val description: String = "",
    val city: String = "",
    val isPrivate: Boolean
)