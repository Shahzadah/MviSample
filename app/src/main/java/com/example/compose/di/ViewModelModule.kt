package com.example.compose.di

import com.example.compose.data.api.ApiEndpoint
import com.example.compose.data.repository.BreedSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideBreedSearchRepository(apiEndpoint: ApiEndpoint) = BreedSearchRepository(apiEndpoint)
}