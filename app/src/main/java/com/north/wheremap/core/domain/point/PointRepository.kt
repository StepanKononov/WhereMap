package com.north.wheremap.core.domain.point

import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.EmptyResult
import kotlinx.coroutines.flow.Flow

interface PointRepository {

    fun getPointsInCollection(collectionId: String): Flow<List<Point>>

    suspend fun upsertPoint(point: Point): EmptyResult<DataError>

    suspend fun deletePointById(pointId: String)

    suspend fun fetchPoints(): EmptyResult<DataError>

    suspend fun deleteAllPoints()
}