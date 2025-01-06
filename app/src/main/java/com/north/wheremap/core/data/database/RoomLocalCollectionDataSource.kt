package com.north.wheremap.core.data.database

import android.database.sqlite.SQLiteFullException
import com.north.wheremap.core.data.database.dao.CollectionDao
import com.north.wheremap.core.data.database.mappers.toDomain
import com.north.wheremap.core.data.database.mappers.toEntity
import com.north.wheremap.core.domain.collection.Collection
import com.north.wheremap.core.domain.collection.CollectionId
import com.north.wheremap.core.domain.collection.CollectionLocalDataSource
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalCollectionDataSource @Inject constructor(
    private val collectionDao: CollectionDao,
) : CollectionLocalDataSource {

    override fun getUserCollection(): Flow<List<Collection>> {
        return collectionDao.getUserCollection()
            .map { runEntities ->
                runEntities.map { it.toDomain() }
            }
    }

    override suspend fun upsertCollection(collection: Collection): Result<CollectionId, DataError.Local> {
        return try {
            val entity = collection.toEntity()
            collectionDao.upsertCollection(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteCollection(id: String) {
        collectionDao.deleteCollection(id)
    }

    override suspend fun deleteAllCollections() {
        collectionDao.deleteAllCollection()
    }

}