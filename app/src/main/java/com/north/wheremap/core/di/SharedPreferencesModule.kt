package com.north.wheremap.core.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Provides
    @Singleton
    @AuthSharedPreferences
    fun provideAuthPreference(application: Application): SharedPreferences {
        return EncryptedSharedPreferences(
            application,
            "auth_pref",
            MasterKey(application),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthSharedPreferences
