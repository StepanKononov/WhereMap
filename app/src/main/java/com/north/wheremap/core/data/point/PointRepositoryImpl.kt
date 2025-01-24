package com.north.wheremap.core.data.point

import com.north.wheremap.core.di.ApplicationScope
import com.north.wheremap.core.di.IoDispatcher
import com.north.wheremap.core.domain.point.Point
import com.north.wheremap.core.domain.point.PointLocalDataSource
import com.north.wheremap.core.domain.point.PointRemoteDataSource
import com.north.wheremap.core.domain.point.PointRepository
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.EmptyResult
import com.north.wheremap.core.domain.utils.Result
import com.north.wheremap.core.domain.utils.asEmptyDataResult
import com.north.wheremap.core.domain.utils.onError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val localPointDataSource: PointLocalDataSource,
    private val pointRemoteDataSource: PointRemoteDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : PointRepository {

    override fun getPointsInCollection(collectionId: String): Flow<List<Point>> {
        return localPointDataSource.getPointsInCollection(collectionId)
    }

    override suspend fun fetchPoints(): EmptyResult<DataError> {
        return withContext(ioDispatcher) {
            when (val result = pointRemoteDataSource.getUserPoints()) {
                is Result.Error -> result.asEmptyDataResult()
                is Result.Success -> {
                    val insertResults = result.data.map { point ->
                        async {
                            localPointDataSource.upsertPoint(point)
                        }
                    }.awaitAll()

                    insertResults.forEach { insertResult ->
                        insertResult.onError {
                            return@withContext Result.Error(it)
                        }
                    }

                    return@withContext Result.Success(Unit)
                }
            }
        }
    }

    override suspend fun deleteAllPoints() {
        localPointDataSource.deleteAllPoints()
    }


    override suspend fun upsertPoint(point: Point): EmptyResult<DataError> {
        val localResult = localPointDataSource.upsertPoint(point)

        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val pointWithId = point.copy(id = localResult.data)

        val remoteResult = applicationScope.async {
            withContext(ioDispatcher) {
                pointRemoteDataSource.upsertPoint(pointWithId)
            }
        }.await()

        return when (remoteResult) {
            is Result.Error -> {
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localPointDataSource.upsertPoint(remoteResult.data)
                        .asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deletePointById(pointId: String) {
        localPointDataSource.deletePointById(pointId)
    }
}