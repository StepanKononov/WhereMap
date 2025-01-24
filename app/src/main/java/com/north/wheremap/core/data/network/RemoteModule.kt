package com.north.wheremap.core.data.network

import com.north.wheremap.core.data.network.collection.CollectionRemoteDataSourceImpl
import com.north.wheremap.core.domain.collection.CollectionRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {


    @Provides
    @Singleton
    fun providesCollectionRemoteDataSource(impl: CollectionRemoteDataSourceImpl): CollectionRemoteDataSource =
        impl
}