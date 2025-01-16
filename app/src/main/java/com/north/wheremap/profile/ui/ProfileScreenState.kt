package com.north.wheremap.profile.ui

import com.north.wheremap.collection.ui.CollectionUI

data class ProfileScreenState(
    val collections: List<CollectionUI> = emptyList()
)