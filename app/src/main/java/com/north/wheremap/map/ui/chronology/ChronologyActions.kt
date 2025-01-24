package com.north.wheremap.map.ui.chronology

sealed class ChronologyActions {
    data object MoveToNextPoint : ChronologyActions()
    data object MoveToPreviousPoint : ChronologyActions()
}