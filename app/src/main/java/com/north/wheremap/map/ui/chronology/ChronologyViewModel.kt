package com.north.wheremap.map.ui.chronology

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.north.wheremap.core.domain.point.PointRepository
import com.north.wheremap.core.navigation.ChronologyRoute
import com.north.wheremap.map.ui.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChronologyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pointRepository: PointRepository
) : ViewModel() {

    private val chronologyRoute: ChronologyRoute = savedStateHandle.toRoute()
    private val collectionId: String = chronologyRoute.id

    private val _state = MutableStateFlow(ChronologyScreenState())
    val state = _state
        .onStart { loadPoints() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), _state.value)

    private var currentIndex: Int = -1

    fun onAction(action: ChronologyActions) {
        when (action) {
            ChronologyActions.MoveToNextPoint -> moveToNextPoint()
            ChronologyActions.MoveToPreviousPoint -> moveToPreviousPoint()
        }
    }

    private fun loadPoints() {
        pointRepository.getPointsInCollection(collectionId)
            .onEach { pointsList ->
                val points = pointsList.map { it.toUi() }
                currentIndex = if (points.isNotEmpty()) 0 else -1
                val selectedPoint = points.getOrNull(currentIndex)
                _state.update { currentState ->
                    currentState.copy(
                        points = points,
                        currentPoint = selectedPoint
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun moveToNextPoint() {
        val points = _state.value.points
        if (currentIndex in points.indices && currentIndex < points.size - 1) {
            currentIndex++
            val nextPoint = points[currentIndex]
            _state.update { it.copy(currentPoint = nextPoint) }
        }
    }

    private fun moveToPreviousPoint() {
        val points = _state.value.points
        if (currentIndex in points.indices && currentIndex > 0) {
            currentIndex--
            val previousPoint = points[currentIndex]
            _state.update { it.copy(currentPoint = previousPoint) }
        }
    }
}