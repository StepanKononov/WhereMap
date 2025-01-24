package com.north.wheremap.core.data.collection

import android.util.Log
import com.north.wheremap.core.di.ApplicationScope
import com.north.wheremap.core.di.IoDispatcher
import com.north.wheremap.core.domain.collection.Collection
import com.north.wheremap.core.domain.collection.CollectionLocalDataSource
import com.north.wheremap.core.domain.collection.CollectionRemoteDataSource
import com.north.wheremap.core.domain.collection.CollectionRepository
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

class CollectionRepositoryImpl @Inject constructor(
    private val localCollectionDataSource: CollectionLocalDataSource,
    private val remoteCollectionDataSource: CollectionRemoteDataSource,
    @ApplicationScope
    private val applicationScope: CoroutineScope,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : CollectionRepository {

    override fun getUserCollection(): Flow<List<Collection>> {
        return localCollectionDataSource.getUserCollection()
    }

    override suspend fun fetchCollections(): EmptyResult<DataError> {
        return withContext(ioDispatcher) {
            when (val result = remoteCollectionDataSource.getUserCollections()) {
                is Result.Error -> result.asEmptyDataResult()
                is Result.Success -> {
                    val insertResults = result.data.map { collection ->
                        async {
                            localCollectionDataSource.upsertCollection(collection)
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

    override suspend fun upsertCollection(collection: Collection): EmptyResult<DataError> {
        val localResult = localCollectionDataSource.upsertCollection(collection)

        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val collectionWithId = collection.copy(id = localResult.data)

        // TODO возможно бред написал =) Логика в том чтобы при отмене upsertCollection (уход с экрана) продолжили выполнее запроса надо проверить
        val remoteResult = applicationScope.async {
            withContext(ioDispatcher) {
                remoteCollectionDataSource.postCollection(collectionWithId)
            }
        }.await()

        return when (remoteResult) {
            is Result.Error -> {
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localCollectionDataSource.upsertCollection(remoteResult.data)
                        .asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteCollectionById(id: String) {
        localCollectionDataSource.deleteCollection(id)
    }

    override suspend fun deleteAllCollections() {
        localCollectionDataSource.deleteAllCollections()
    }
}