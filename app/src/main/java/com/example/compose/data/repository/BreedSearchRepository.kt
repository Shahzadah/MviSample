package com.example.compose.data.repository

import com.example.compose.data.api.ApiEndpoint
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreedSearchRepository constructor(private val apiEndpoint: ApiEndpoint) {

    suspend fun fetchBreedsList(): Results<List<BreedDetails>, Throwable> {
        try {
            val response = withContext(Dispatchers.IO) { apiEndpoint.fetchBreedsList() }
            response.body()?.takeIf { response.isSuccessful }?.let {
                return Results.Success(it)
            } ?: kotlin.run {
                response.errorBody()
                return Results.Error(NullPointerException())
            }
        } catch (e: Exception) {
            return Results.Error(e)
        }
    }
}