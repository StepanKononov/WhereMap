package com.north.wheremap.collection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.core.domain.collection.Collection
import com.north.wheremap.core.domain.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        CreateCollectionScreenState()
    )

    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)

    private val eventChannel = Channel<CreateCollectionEvents>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: CreateCollectionActions) {
        when (action) {
            CreateCollectionActions.ConfirmSaveCollection -> saveCollection()
            is CreateCollectionActions.UpdateCollectionDescription -> updateCollectionDescription(action.description)
            is CreateCollectionActions.UpdateCollectionName -> updateCollectionName(action.name)
            is CreateCollectionActions.UpdateCollectionCity -> updateCollectionCity(action.city)
            is CreateCollectionActions.UpdateCollectionPrivacy -> updateCollectionPrivacy(action.isPrivate)
        }
    }

    private fun saveCollection() {
        viewModelScope.launch {
            val curState = state.value
            val collection = Collection(
                name = curState.collectionName,
                description = curState.collectionDescription.ifBlank { "" },
                isPrivate = curState.isCollectionPrivate,
                city = curState.collectionCity,
            )
            collectionRepository.upsertCollection(collection)
            eventChannel.send(CreateCollectionEvents.Confirm)
        }
    }

    private fun updateCollectionDescription(description: String) {
        _state.update { it.copy(collectionDescription = description) }
    }

    private fun updateCollectionName(name: String) {
        _state.update { it.copy(collectionName = name) }
    }

    private fun updateCollectionCity(name: String) {
        _state.update { it.copy(collectionCity = name) }
    }

    private fun updateCollectionPrivacy(isPrivate: Boolean) {
        _state.update { it.copy(isCollectionPrivate = isPrivate) }
    }
}
