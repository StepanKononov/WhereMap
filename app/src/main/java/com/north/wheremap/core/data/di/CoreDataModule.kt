package com.north.wheremap.core.data.di

import com.north.wheremap.core.data.auth.EncryptedSessionStorage
import com.north.wheremap.core.data.collection.CollectionRepositoryImpl
import com.north.wheremap.core.data.networking.HttpClientFactory
import com.north.wheremap.core.data.point.PointRepositoryImpl
import com.north.wheremap.core.domain.auth.SessionStorage
import com.north.wheremap.core.domain.collection.CollectionRepository
import com.north.wheremap.core.domain.point.PointRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CoreDataModule {

    @Provides
    @Singleton
    fun provideCollectionRepository(impl: CollectionRepositoryImpl): CollectionRepository = impl

    @Provides
    @Singleton
    fun providePointRepository(impl: PointRepositoryImpl): PointRepository = impl

    @Provides
    @Singleton
    fun provideSessionStorage(impl: EncryptedSessionStorage): SessionStorage = impl

    @Provides
    @Singleton
    fun provideHttpClientFactory(factory: HttpClientFactory): HttpClient = factory.build()

}