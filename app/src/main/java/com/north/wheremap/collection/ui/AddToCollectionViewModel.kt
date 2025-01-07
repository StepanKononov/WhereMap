package com.north.wheremap.collection.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.location.Location
import com.north.wheremap.core.domain.point.PointRepository
import com.north.wheremap.core.navigation.AddToCollectionRoute
import com.north.wheremap.core.navigation.CustomNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class AddToCollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val collectionRepository: CollectionRepository,
    private val pointRepository: PointRepository,
) : ViewModel() {

    private val params: AddToCollectionRoute = savedStateHandle.toRoute(
        typeMap = mapOf(
            typeOf<Location>() to CustomNavType.LocationType
        )
    )

    private val _state = MutableStateFlow(
        AddToCollectionScreenState(
            point = params.point,
        )
    )

    val state = _state
        .onStart { loadInitialData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _state.value
        )

    private val eventChannel = Channel<AddToCollectionEvents>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(actions: AddToCollectionActions) {
        viewModelScope.launch {
            when (actions) {
                AddToCollectionActions.ConfirmSavePoint -> {
                    //TODO: save point...
                    eventChannel.send(AddToCollectionEvents.Confirm)
                }

                is AddToCollectionActions.SetSelectedCollection -> {
                    _state.update {
                        it.copy(
                            selectedCollectionId = actions.collectionId
                        )
                    }
                }

                is AddToCollectionActions.UpdatePointDescription -> {
                    _state.update {
                        it.copy(
                            pointDescription = actions.description
                        )
                    }
                }

                is AddToCollectionActions.UpdatePointName -> {
                    _state.update {
                        it.copy(
                            pointName = actions.name
                        )
                    }
                }

                AddToCollectionActions.CreateNewCollection -> {
                    eventChannel.send(AddToCollectionEvents.NavigateToCreateCollectionScreen)
                }
            }
        }
    }

    private fun loadInitialData() {
        collectionRepository.getUserCollection().onEach { collection ->
            val collections = collection.map { it.toUI() }
            _state.update { state ->
                state.copy(
                    collections = collections,
                    selectedCollectionId = collections.firstOrNull()?.id
                )
            }
        }.launchIn(viewModelScope)
    }
}

