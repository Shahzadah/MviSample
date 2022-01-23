package com.example.compose.data.repository

import com.example.compose.data.api.ApiEndpoint
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.Results
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class BreedSearchRepository constructor(private val apiEndpoint: ApiEndpoint) {

    suspend fun fetchBreedsList(): Results<List<BreedDetails>, String> {
        return try {
            val response = apiEndpoint.fetchBreedsList()
            response.body()?.takeIf { response.isSuccessful }?.let {
                Results.Success(it)
            } ?: kotlin.run {
                Results.Error(response.errorBody().toString())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> Results.Error(getErrorMessage(throwable.code()))
                is IOException -> Results.Error("No network")
                else -> Results.Error(getErrorMessage(Int.MAX_VALUE))
            }
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> "Unauthorised"
            HttpURLConnection.HTTP_FORBIDDEN -> "Forbidden"
            HttpURLConnection.HTTP_BAD_REQUEST -> "Bad request"
            HttpURLConnection.HTTP_INTERNAL_ERROR -> "Internal server error"
            HttpURLConnection.HTTP_NOT_FOUND -> "Resource not found"
            else -> "Something went wrong"
        }
    }
}