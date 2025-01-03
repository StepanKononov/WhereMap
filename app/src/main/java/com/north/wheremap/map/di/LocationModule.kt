package com.north.wheremap.map.di

import com.north.wheremap.map.location.AndroidLocationObserver
import com.north.wheremap.map.location.LocationObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class LocationModule {
    @Binds
    abstract fun bindLocation(impl: AndroidLocationObserver): LocationObserver
}