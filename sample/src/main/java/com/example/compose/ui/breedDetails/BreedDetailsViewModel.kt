package com.example.compose.ui.breedDetails

import com.example.compose.ui.breedDetails.mvi.BreedDetailsEffect
import com.example.compose.ui.breedDetails.mvi.BreedDetailsIntent
import com.example.compose.ui.breedDetails.mvi.BreedDetailsState
import com.example.core_mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor() :
    MviViewModel<BreedDetailsState, BreedDetailsIntent, BreedDetailsEffect>() {

    override val defaultState = BreedDetailsState(loading = true)

    override fun receiveIntent(intent: BreedDetailsIntent) {
    }
}