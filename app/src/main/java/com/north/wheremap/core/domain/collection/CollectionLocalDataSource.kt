package com.north.wheremap.core.domain.collection

import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

typealias CollectionId = String

interface CollectionLocalDataSource {

    fun getUserCollection(): Flow<List<Collection>>

    suspend fun upsertCollection(collection: Collection): Result<CollectionId, DataError.Local>

    suspend fun deleteCollection(id: String)

    suspend fun deleteAllCollections()
}