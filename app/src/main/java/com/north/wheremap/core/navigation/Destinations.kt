package com.north.wheremap.core.navigation

import com.north.wheremap.map.location.Location
import kotlinx.serialization.Serializable

@Serializable
data object MainGraphRoute

@Serializable
data object FeedsListRoute

@Serializable
data object MapRoute

@Serializable
data object ProfileRoute

@Serializable
data class ChronologyRoute(val id: Long)

@Serializable
data class AddToCollectionRoute(val point: Location)