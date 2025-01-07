package com.north.wheremap.collection.ui

sealed interface AddToCollectionActions {

    data class UpdatePointName(val name: String) : AddToCollectionActions
    data class UpdatePointDescription(val description: String) : AddToCollectionActions
    data class SetSelectedCollection(val collectionId: String) : AddToCollectionActions
    data object ConfirmSavePoint : AddToCollectionActions
    data object CreateNewCollection : AddToCollectionActions
}