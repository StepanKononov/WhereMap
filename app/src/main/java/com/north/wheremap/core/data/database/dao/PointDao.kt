package com.north.wheremap.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.north.wheremap.core.data.database.entity.PointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Query("SELECT * FROM point WHERE collection_id = :collectionId")
    fun getPointsByCollection(collectionId: String): Flow<List<PointEntity>>

    @Upsert
    suspend fun upsertPoint(point: PointEntity)

    @Query("DELETE FROM point WHERE id=:id")
    suspend fun deletePoint(id: String)

    @Query("DELETE FROM point")
    fun deleteAllPoints()

}