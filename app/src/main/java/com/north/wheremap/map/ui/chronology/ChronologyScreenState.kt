package com.north.wheremap.map.ui.chronology

import androidx.compose.runtime.Immutable
import com.north.wheremap.map.ui.PointUI

@Immutable
data class ChronologyScreenState(
    val points: List<PointUI> = emptyList(),
    val currentPoint: PointUI? = null
)