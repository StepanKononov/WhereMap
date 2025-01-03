package com.north.wheremap.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainGraph

@Serializable
data object FeedsList

@Serializable
data object Map

@Serializable
data object Profile

@Serializable
data class Chronology(val id: Long)
