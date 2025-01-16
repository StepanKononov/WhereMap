package com.north.wheremap.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.collection.ui.toUI
import com.north.wheremap.core.domain.auth.SessionStorage
import com.north.wheremap.core.domain.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state

    private val eventChannel = Channel<ProfileScreenEvents>()
    val events = eventChannel.receiveAsFlow()

    init {
        loadUserCollections()
    }

    private fun loadUserCollections() {
        viewModelScope.launch {
            collectionRepository.getUserCollection().collect { collections ->
                _state.value = _state.value.copy(collections = collections.map { it.toUI() })
            }
        }
    }

    fun onAction(action: ProfileScreenActions) {
        when (action) {
            ProfileScreenActions.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
//            sessionStorage.clear()
            eventChannel.send(ProfileScreenEvents.NavigateToAuth)
        }
    }
}
