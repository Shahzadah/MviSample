package com.example.compose.data.model

sealed class Results<out S, out E> {
    data class Success<out S>(val response: S) : Results<S, Nothing>()

    data class Error<out E>(val message: E) : Results<Nothing, E>()
}