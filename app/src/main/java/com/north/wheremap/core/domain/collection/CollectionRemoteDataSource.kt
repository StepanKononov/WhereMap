package com.north.wheremap.core.domain.collection

import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result


interface CollectionRemoteDataSource {

    suspend fun getUserCollections(): Result<List<Collection>, DataError.Network>

    suspend fun postCollection(collection: Collection): Result<Collection, DataError.Network>

    suspend fun deleteCollection(id: String)

    suspend fun deleteAllCollections()
}