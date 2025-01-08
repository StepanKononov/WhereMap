package com.north.wheremap.core.data.point

import com.north.wheremap.core.di.IoDispatcher
import com.north.wheremap.core.domain.point.Point
import com.north.wheremap.core.domain.point.PointLocalDataSource
import com.north.wheremap.core.domain.point.PointRepository
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.EmptyResult
import com.north.wheremap.core.domain.utils.asEmptyDataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val localPointDataSource: PointLocalDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : PointRepository {

    override fun getPointsInCollection(collectionId: String): Flow<List<Point>> {
        return localPointDataSource.getPointsInCollection(collectionId)
    }

    override suspend fun upsertPoint(point: Point): EmptyResult<DataError> {
        return withContext(ioDispatcher) {
            localPointDataSource.upsertPoint(point)
        }.asEmptyDataResult()
    }

    override suspend fun deletePointById(pointId: String) {
        localPointDataSource.deletePointById(pointId)
    }
}