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
    val id: Int = chronologyRoute.id


    // Пойти в PointLocalDataSource и получить список Point по id коллекции
    // Созадть events: MoveToPoint(location)
    // В Actions - переход к следующей/предыдущей точке
    // На UI: слушать events, на каждый MoveToPoint переводить камеру на нужную точку (см flyToLocation)
    //        отобажать список точек на карте, см renderLocationMarker

}
