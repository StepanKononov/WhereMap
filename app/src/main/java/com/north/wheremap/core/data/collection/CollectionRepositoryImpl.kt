package com.north.wheremap.core.data.collection

import com.north.wheremap.core.domain.collection.Collection
import com.north.wheremap.core.domain.collection.CollectionLocalDataSource
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.EmptyResult
import com.north.wheremap.core.domain.utils.asEmptyDataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val localCollectionDataSource: CollectionLocalDataSource,
) : CollectionRepository {

    override fun getUserCollection(): Flow<List<Collection>> {
        return localCollectionDataSource.getUserCollection()
    }

    override suspend fun upsertCollection(collection: Collection): EmptyResult<DataError> {
        val localResult = localCollectionDataSource.upsertCollection(collection)
        return localResult.asEmptyDataResult()
    }

    override suspend fun deleteCollectionById(id: String) {
        localCollectionDataSource.deleteCollection(id)
    }

    override suspend fun deleteAllCollections() {
        localCollectionDataSource.deleteAllCollections()
    }
}