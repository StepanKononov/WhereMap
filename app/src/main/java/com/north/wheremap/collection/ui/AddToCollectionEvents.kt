package com.north.wheremap.collection.ui

sealed interface AddToCollectionEvents {

    data object Confirm : AddToCollectionEvents
    data object NavigateToCreateCollectionScreen : AddToCollectionEvents
}
