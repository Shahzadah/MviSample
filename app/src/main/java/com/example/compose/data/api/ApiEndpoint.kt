package com.example.compose.data.api

import com.example.compose.data.model.BreedDetails
import retrofit2.Response
import retrofit2.http.GET

interface ApiEndpoint {

    @GET("breeds/")
    suspend fun fetchBreedsList(): Response<List<BreedDetails>>
}
