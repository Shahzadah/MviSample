package com.example.compose.ui.breedSearch.mvi

import com.example.compose.data.model.BreedDetails
import com.example.compose.utils.MviEffect

sealed class BreedListEffect : MviEffect {
    data class LaunchBreedDetailsScreen(val breedDetails: BreedDetails) : BreedListEffect()
}