package com.north.wheremap.core.data.database.di

import android.content.Context
import androidx.room.Room
import com.north.wheremap.core.data.database.AppDatabase
import com.north.wheremap.core.data.database.RoomLocalCollectionDataSource
import com.north.wheremap.core.data.database.RoomLocalPointDataSource
import com.north.wheremap.core.domain.collection.CollectionLocalDataSource
import com.north.wheremap.core.domain.point.PointLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "map.db",
        ).build()
    }

    @Provides
    @Singleton
    fun providesCollectionLocalDataSource(impl: RoomLocalCollectionDataSource):
            CollectionLocalDataSource = impl

    @Provides
    @Singleton
    fun providesPointLocalDataSource(impl: RoomLocalPointDataSource):
            PointLocalDataSource = impl

    @Provides
    @Singleton
    fun providesCollectionDao(db: AppDatabase) = db.collectionDao

    @Provides
    @Singleton
    fun providesPointDao(db: AppDatabase) = db.pointDao
}