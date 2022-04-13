package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.core_mvi.MviState

sealed class BreedListState: MviState {
    data class BreedListContentState(
        val searchText: String = "",
        val listBreeds: List<BreedDetails>) : BreedListState()
    object BreedListLoadingState: BreedListState()
}
