package com.north.wheremap.core.domain.point

import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

typealias PointId = String

interface PointLocalDataSource {

    fun getPointsInCollection(collectionId: String): Flow<List<Point>>

    suspend fun upsertPoint(point: Point): Result<PointId, DataError.Local>

    suspend fun deletePointById(pointId: String)

    suspend fun deleteAllPoints()
}