package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.compose.utils.MviIntent

sealed class BreedListIntent : MviIntent {
    data class BreedsListItemClicked(val breedDetails: BreedDetails) : BreedListIntent()
}
