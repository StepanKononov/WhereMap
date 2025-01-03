package com.north.wheremap.map.location

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<Location>
}
