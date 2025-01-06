package com.north.wheremap.collection.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.north.wheremap.core.navigation.AddToCollectionRoute
import com.north.wheremap.core.navigation.CustomNavType
import com.north.wheremap.core.domain.location.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class AddToCollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val params: AddToCollectionRoute = savedStateHandle.toRoute(
        typeMap = mapOf(
            typeOf<Location>() to CustomNavType.LocationType
        )
    )

    private val _state = MutableStateFlow(
        AddToCollectionScreenState(
            point = params.point
        )
    )

    val state = _state.asStateFlow()



}

