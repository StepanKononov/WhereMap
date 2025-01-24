package com.north.wheremap.core.domain.point

import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result

interface PointRemoteDataSource {

    suspend fun getPointsInCollection(collectionId: String): Result<List<Point>, DataError.Network>

    suspend fun upsertPoint(point: Point): Result<Point, DataError.Network>

    suspend fun getUserPoints(): Result<List<Point>, DataError.Network>
}