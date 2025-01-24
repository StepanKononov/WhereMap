package com.north.wheremap.core.data.network.collection

import com.north.wheremap.core.data.networking.get
import com.north.wheremap.core.data.networking.post
import com.north.wheremap.core.domain.collection.Collection
import com.north.wheremap.core.domain.collection.CollectionRemoteDataSource
import com.north.wheremap.core.domain.utils.DataError
import com.north.wheremap.core.domain.utils.Result
import com.north.wheremap.core.domain.utils.map
import io.ktor.client.HttpClient
import javax.inject.Inject

class CollectionRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : CollectionRemoteDataSource {

    override suspend fun getUserCollections(): Result<List<Collection>, DataError.Network> {
        return httpClient.get<List<CollectionDto>>(
            route = "/mycollections",
        ).map { collectionDtos ->
            collectionDtos.map { it.toCollection() }
        }
    }

    override suspend fun postCollection(collection: Collection): Result<Collection, DataError.Network> {
        return httpClient.post<Collection, CollectionDto>(
            route = "/collections",
            body = collection
        ).map { it.toCollection() }

    }

    override suspend fun deleteCollection(id: String) = Unit

    override suspend fun deleteAllCollections() = Unit
}