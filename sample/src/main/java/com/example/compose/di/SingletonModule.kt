package com.example.compose.di

import com.example.compose.BuildConfig
import com.example.compose.data.api.ServiceClient
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    fun provideServiceClient() = ServiceClient(BuildConfig.BASE_API_URL, Gson())

    @Provides
    fun provideApiEndpoint(serviceClient: ServiceClient) = serviceClient.getRestService()
}