package com.north.wheremap.auth.di

import com.north.wheremap.auth.data.AuthRepositoryImpl
import com.north.wheremap.auth.data.EmailPatternValidator
import com.north.wheremap.auth.domain.AuthRepository
import com.north.wheremap.auth.domain.PatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthDataModule {

    @Provides
    @Singleton
    fun providePatternValidator(impl: EmailPatternValidator): PatternValidator = impl

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

}