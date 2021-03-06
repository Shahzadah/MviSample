package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.core_mvi.MviIntent

sealed class BreedListIntent : MviIntent {
    data class BreedsListItemClicked(val breedDetails: BreedDetails) : BreedListIntent()
    data class SearchTextChanged(val searchText: String) : BreedListIntent()
}
