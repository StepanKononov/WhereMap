package com.north.wheremap.core.data.di

import com.north.wheremap.core.data.auth.EncryptedSessionStorage
import com.north.wheremap.core.data.collection.CollectionRepositoryImpl
import com.north.wheremap.core.data.point.PointRepositoryImpl
import com.north.wheremap.core.domain.auth.SessionStorage
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.point.PointRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface CoreDataModule {

    @Binds
    @Singleton
    fun bindCollectionRepository(impl: CollectionRepositoryImpl): CollectionRepository

    @Binds
    @Singleton
    fun bindPointRepository(impl: PointRepositoryImpl): PointRepository

    @Binds
    @Singleton
    fun bindSessionStorage(impl: EncryptedSessionStorage): SessionStorage

}