package com.example.compose.ui.breedSearch

import androidx.lifecycle.viewModelScope
import com.example.compose.data.model.Results
import com.example.compose.data.repository.BreedSearchRepository
import com.example.compose.ui.breedSearch.mvi.BreedListEffect
import com.example.compose.ui.breedSearch.mvi.BreedListIntent
import com.example.compose.ui.breedSearch.mvi.BreedListState
import com.example.compose.ui.breedSearch.mvi.BreedListStateWrapper
import com.example.compose.utils.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(private val repository: BreedSearchRepository) :
    MviViewModel<BreedListStateWrapper, BreedListIntent, BreedListEffect>() {

    override val defaultState: BreedListStateWrapper = BreedListStateWrapper(BreedListState.Loading)

    init {
        viewModelScope.launch {
            callBreedListApi()
        }
    }

    override fun receiveIntent(intent: BreedListIntent) {
        when (intent) {
            is BreedListIntent.BreedsListItemClicked -> {
                postEffect(BreedListEffect.LaunchBreedDetailsScreen(intent.breedDetails))
            }
        }
    }

    private suspend fun callBreedListApi() {
        when (val results = repository.fetchBreedsList()) {
            is Results.Success -> {
                reduceState { copy(current = BreedListState.Information(results.response)) }
            }
            is Results.Error -> {
                reduceState { copy(current = BreedListState.Error(results.throwable)) }
            }
        }
    }
}