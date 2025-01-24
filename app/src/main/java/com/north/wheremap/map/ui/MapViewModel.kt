package com.north.wheremap.map.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.core.di.ApplicationScope
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.location.Location
import com.north.wheremap.map.location.LocationObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModel @Inject constructor(
    private val locationObserver: LocationObserver,
    private val collectionRepository: CollectionRepository,
    @ApplicationScope
    private val applicationScope: CoroutineScope,
) : ViewModel() {

    private val isObservingLocation = MutableStateFlow(false)

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if(isObservingLocation) {
                locationObserver.observeLocation(1000L)
            } else flowOf()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )

    init {
        // TODO: это не тут должно быть, а во ViewModel экрана с табами? Но тут просто первый экран после логина
        applicationScope.launch {
            collectionRepository.fetchCollections()
        }
    }


    private val _selectedPoint = MutableStateFlow<Location?>(null)
    val selectedPoint = _selectedPoint.asStateFlow()

    private val eventChannel = Channel<MapEvents>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: MapScreenActions) {
        when(action){
            is MapScreenActions.SubmitLocationPermission -> {
                isObservingLocation.value = action.acceptedLocationPermission
            }

            is MapScreenActions.SetNewPoint -> {
                _selectedPoint.value = action.location
                viewModelScope.launch {
                    eventChannel.send(MapEvents.ZoomToPoint(action.location))
                }
            }

            MapScreenActions.ResetSelectedPoint ->  {
                _selectedPoint.value = null
            }
        }
    }

}