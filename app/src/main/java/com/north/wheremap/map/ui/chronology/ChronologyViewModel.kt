package com.north.wheremap.map.ui.chronology

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.north.wheremap.core.navigation.ChronologyRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChronologyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chronologyRoute: ChronologyRoute = savedStateHandle.toRoute()
    val id: Long = chronologyRoute.id
}
