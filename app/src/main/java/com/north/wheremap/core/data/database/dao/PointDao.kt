package com.north.wheremap.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.north.wheremap.core.data.database.entity.PointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Upsert
    suspend fun upsertPoint(point: PointEntity)

    @Query("SELECT * FROM point WHERE collection_id = :collectionId")
    fun getPointsByCollectionFlow(collectionId: String): Flow<List<PointEntity>>

}