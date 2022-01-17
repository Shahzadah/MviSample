package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.core_mvi.MviState

data class BreedListState(
    var loading: Boolean,
    var searchText: String = "",
    var listBreeds: List<BreedDetails>? = null,
    var filteredListBreeds: List<BreedDetails>? = null
) : MviState
