package com.north.wheremap.core.data.database

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteFullException
import com.north.wheremap.core.data.database.dao.PointDao
import com.north.wheremap.core.data.database.mappers.toDomain
import com.north.wheremap.core.data.database.mappers.toEntity
import com.north.wheremap.core.domain.point.Point
import com.north.wheremap.core.domain.point.PointId
import com.north.wheremap.core.domain.point.PointLocalDataSource
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalPointDataSource @Inject constructor(
    private val pointDao: PointDao,
) : PointLocalDataSource {

    override fun getPointsInCollection(collectionId: String): Flow<List<Point>> {
        return pointDao.getPointsByCollection(collectionId)
            .map { pointEntities ->
                pointEntities.map { it.toDomain() }
            }
    }

    override suspend fun upsertPoint(point: Point): Result<PointId, DataError.Local> {
        return try {
            val entity = point.toEntity()
            pointDao.upsertPoint(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: SQLiteConstraintException) {
            Result.Error(DataError.Local.CONSTRAINT_VIOLATION)
        }
    }

    override suspend fun deletePointById(pointId: String) {
        pointDao.deletePoint(pointId)
    }
}