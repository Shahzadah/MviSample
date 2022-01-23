package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.core_mvi.MviEffect

sealed class BreedListEffect : MviEffect {
    data class LaunchBreedDetailsScreen(val breedDetails: BreedDetails) : BreedListEffect()
    data class ShowError(val error: String) : BreedListEffect()
}