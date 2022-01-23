package com.example.compose.data.api

import com.example.compose.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceClient constructor(private val apiBaseUrl: String, private val gson: Gson) {

    fun getRestService(): ApiEndpoint {
        val builder = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(loggingInterceptor)
            }
        }

        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiEndpoint::class.java)
    }
}