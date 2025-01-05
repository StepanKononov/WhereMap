package com.north.wheremap.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DispatcherEntryPoint {
    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher

    @MainDispatcherImmediate
    fun mainImmediate(): CoroutineDispatcher
}