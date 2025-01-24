package com.north.wheremap.core.domain.collection

import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.EmptyResult
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    fun getUserCollection(): Flow<List<Collection>>

    suspend fun fetchCollections(): EmptyResult<DataError>

    suspend fun upsertCollection(collection: Collection): EmptyResult<DataError>

    suspend fun deleteCollectionById(id: String)

    suspend fun deleteAllCollections()
}