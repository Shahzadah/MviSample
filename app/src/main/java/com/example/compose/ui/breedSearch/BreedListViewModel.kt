package com.example.compose.ui.breedSearch

import androidx.lifecycle.viewModelScope
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.Results
import com.example.compose.data.repository.BreedSearchRepository
import com.example.compose.ui.breedSearch.mvi.BreedListEffect
import com.example.compose.ui.breedSearch.mvi.BreedListIntent
import com.example.compose.ui.breedSearch.mvi.BreedListState
import com.example.core_mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(private val repository: BreedSearchRepository) :
    MviViewModel<BreedListState, BreedListIntent, BreedListEffect>() {

    override val defaultState: BreedListState = BreedListState(loading = true)

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
            is BreedListIntent.SearchTextChanged -> {
                val filteredList = if (intent.searchText.isEmpty()) {
                    state.value.listBreeds
                } else {
                    val tempList = ArrayList<BreedDetails>()
                    state.value.listBreeds?.forEach {
                        if (it.name.contains(intent.searchText, ignoreCase = true)) {
                            tempList.add(it)
                        }
                    }
                    tempList
                }
                reduceState { copy(filteredListBreeds = filteredList) }
            }
        }
    }

    private suspend fun callBreedListApi() {
        when (val results = repository.fetchBreedsList()) {
            is Results.Success -> {
                reduceState {
                    copy(
                        loading = false,
                        listBreeds = results.response,
                        filteredListBreeds = results.response
                    )
                }
            }
            is Results.Error -> {
                postEffect(BreedListEffect.ShowError(results.throwable.message!!))
            }
        }
    }
}