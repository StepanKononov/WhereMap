package com.north.wheremap.collection.ui

import com.north.wheremap.core.domain.location.Location

data class AddToCollectionScreenState(
    val point: Location,
    val collections: List<CollectionUI> = emptyList(),
    val selectedCollectionId: String? = null,
    val errorMessage: String? = null,
    val pointName: String = "",
    val pointDescription: String = "",
)