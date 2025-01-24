package com.north.wheremap.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.collection.ui.toUI
import com.north.wheremap.core.di.ApplicationScope
import com.north.wheremap.core.domain.auth.SessionStorage
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.point.PointRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val sessionStorage: SessionStorage,
    private val pointRepository: PointRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
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
        applicationScope.launch {
            collectionRepository.deleteAllCollections()
            pointRepository.deleteAllPoints()
            sessionStorage.set(null)
        }
        viewModelScope.launch {
            eventChannel.send(ProfileScreenEvents.NavigateToAuth)
        }
    }
}
