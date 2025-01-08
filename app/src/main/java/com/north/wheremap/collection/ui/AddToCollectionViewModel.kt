package com.north.wheremap.collection.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.location.Location
import com.north.wheremap.core.domain.point.Point
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

    private val params = savedStateHandle.toRoute<AddToCollectionRoute>(
        typeMap = mapOf(
            typeOf<Location>() to CustomNavType.LocationType
        )
    )

    private val _state = MutableStateFlow(
        AddToCollectionScreenState(point = params.point)
    )

    val state = _state
        .onStart { loadInitialData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)

    private val eventChannel = Channel<AddToCollectionEvents>()
    val events = eventChannel.receiveAsFlow()


    // TODO: хозяйке на заметку.
    //  C OneTimeEvents нужно еще думать. возможно лучше хранить их в стейте месте с
    /*
    class UiEvent<T> {
        val data
        fun onConsumed: () -> Unit
     */
    // Так как ViewModel может быть уничтожен системой а при повторной активации мы захотим востоновить стейт
    // Можно ли соханить Channel??

    fun onAction(action: AddToCollectionActions) {
        when (action) {
            AddToCollectionActions.ConfirmSavePoint -> savePoint()
            is AddToCollectionActions.SetSelectedCollection -> setSelectedCollection(action.collectionId)
            is AddToCollectionActions.UpdatePointDescription -> updatePointDescription(action.description)
            is AddToCollectionActions.UpdatePointName -> updatePointName(action.name)
            AddToCollectionActions.CreateNewCollection -> navigateToCreateCollectionScreen()
        }
    }

    private fun savePoint() {
        viewModelScope.launch {
            val curState = state.value
            val point = Point(
                collectionId = requireNotNull(curState.selectedCollectionId),
                latitude = params.point.lat,
                longitude = params.point.long,
                name = curState.pointName.ifBlank { params.point.toName() },
                description = curState.pointDescription.ifBlank { "" }
            )
            pointRepository.upsertPoint(point)
            eventChannel.send(AddToCollectionEvents.Confirm)
        }
    }

    private fun setSelectedCollection(collectionId: String) {
        _state.update { it.copy(selectedCollectionId = collectionId) }
    }

    private fun updatePointDescription(description: String) {
        _state.update { it.copy(pointDescription = description) }
    }

    private fun updatePointName(name: String) {
        _state.update { it.copy(pointName = name) }
    }

    private fun navigateToCreateCollectionScreen() {
        viewModelScope.launch {
            eventChannel.send(AddToCollectionEvents.NavigateToCreateCollectionScreen)
        }
    }

    private fun loadInitialData() {
        collectionRepository.getUserCollection().onEach { collection ->
            val collections = collection.map { it.toUI() }
            _state.update { state ->
                state.copy(
                    collections = collections, selectedCollectionId = collections.firstOrNull()?.id
                )
            }
        }.launchIn(viewModelScope)
    }
}

internal fun Location.toName(): String {
    return "%.2f  %.2f".format(lat, long)
}
