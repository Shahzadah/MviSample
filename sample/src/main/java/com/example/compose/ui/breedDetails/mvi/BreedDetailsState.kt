package com.example.compose.ui.breedDetails.mvi

import com.example.core_mvi.MviState

data class BreedDetailsState(
    var loading: Boolean,
) : MviState
