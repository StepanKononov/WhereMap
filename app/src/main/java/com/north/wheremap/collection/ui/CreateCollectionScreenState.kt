package com.north.wheremap.collection.ui


data class CreateCollectionScreenState(
    val errorMessage: String? = null,
    val collectionName: String = "",
    val collectionDescription: String = "",
    val collectionCity: String? = null,
    val isCollectionPrivate: Boolean = true,
)