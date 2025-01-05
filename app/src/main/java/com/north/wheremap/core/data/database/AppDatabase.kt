package com.north.wheremap.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.north.wheremap.core.data.database.dao.CollectionDao
import com.north.wheremap.core.data.database.dao.PointDao
import com.north.wheremap.core.data.database.entity.CollectionEntity
import com.north.wheremap.core.data.database.entity.PointEntity


@Database(
    entities = [
        CollectionEntity::class,
        PointEntity::class,
    ],
    exportSchema = false,
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
    abstract fun pointDao(): PointDao
}