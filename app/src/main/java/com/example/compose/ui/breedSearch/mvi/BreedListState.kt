package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.compose.utils.MviState

data class BreedListStateWrapper(val current: BreedListState) : MviState

sealed class BreedListState {
    object Loading : BreedListState()
    data class Information(val listBreeds: List<BreedDetails>) : BreedListState()
    data class Error(val throwable: Throwable) : BreedListState()
}
