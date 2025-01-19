package com.north.wheremap.collection.ui

sealed interface CreateCollectionActions {
    data class UpdateCollectionName(val name: String) : CreateCollectionActions
    data class UpdateCollectionCity(val city: String) : CreateCollectionActions
    data class UpdateCollectionPrivacy(val isPrivate: Boolean) : CreateCollectionActions
    data class UpdateCollectionDescription(val description: String) : CreateCollectionActions
    data object ConfirmSaveCollection : CreateCollectionActions
}