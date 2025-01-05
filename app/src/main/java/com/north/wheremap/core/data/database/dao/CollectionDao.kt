package com.north.wheremap.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.north.wheremap.core.data.database.entity.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Upsert
    suspend fun upsertCollection(run: CollectionEntity)

    @Query("SELECT * FROM collection")
    fun getUserCollection(): Flow<List<CollectionEntity>>

    @Query("DELETE FROM collection WHERE id=:id")
    suspend fun deleteCollection(id: String)

    @Query("DELETE FROM collection")
    suspend fun deleteAllCollection()
}