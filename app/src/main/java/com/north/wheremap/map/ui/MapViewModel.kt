package com.north.wheremap.map.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.north.wheremap.map.location.Location
import com.north.wheremap.map.location.LocationObserver
import dagger.hilt.android.lifecycle.HiltViewModel
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