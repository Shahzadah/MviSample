package com.example.compose.data.repository

import com.example.compose.data.api.ApiEndpoint
import com.example.compose.data.model.BreedDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreedSearchRepository constructor(private val apiEndpoint: ApiEndpoint) {

    suspend fun fetchBreedsList(onError: (String) -> Unit): List<BreedDetails>? {
        return try {
            val response = withContext(Dispatchers.IO) { apiEndpoint.fetchBreedsList() }
            response.body()?.takeIf { response.isSuccessful }
                ?: run {
                    onError.invoke(response.message())
                    null
                }
        } catch (e: Exception) {
            onError.invoke(e.localizedMessage)
            null
        }
    }
}