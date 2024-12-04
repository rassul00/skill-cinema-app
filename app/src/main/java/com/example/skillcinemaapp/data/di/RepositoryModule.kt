package com.example.skillcinemaapp.data.di

import com.example.skillcinemaapp.data.network.ApiService
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import com.example.skillcinemaapp.data.room.CollectionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, dao: CollectionDao): RepositoryImpl {
        return RepositoryImpl(apiService, dao)
    }
}