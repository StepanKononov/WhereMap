package com.north.wheremap.collection.ui

sealed interface CreateCollectionEvents {

    data object Confirm : CreateCollectionEvents
    data object NavigateBack : CreateCollectionEvents
}
