package com.north.wheremap.map.location

import com.north.wheremap.core.domain.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<Location>
}
