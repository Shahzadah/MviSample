package com.example.compose.ui.breedSearch

import androidx.lifecycle.viewModelScope
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.Results
import com.example.compose.ui.breedSearch.domain.FetchBreedListUseCase
import com.example.compose.ui.breedSearch.mvi.BreedListEffect
import com.example.compose.ui.breedSearch.mvi.BreedListIntent
import com.example.compose.ui.breedSearch.mvi.BreedListState
import com.example.core_mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(private val fetchBreedListUseCase: FetchBreedListUseCase) :
    MviViewModel<BreedListState, BreedListIntent, BreedListEffect>() {

    private var breedList = listOf<BreedDetails>()

    override val defaultState: BreedListState = BreedListState.BreedListLoadingState

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
                    breedList
                } else {
                    val tempList = ArrayList<BreedDetails>()
                    breedList.forEach {
                        if (it.name.contains(intent.searchText, ignoreCase = true)) {
                            tempList.add(it)
                        }
                    }
                    tempList
                }
                reduceState {
                    BreedListState.BreedListContentState(intent.searchText, filteredList)
                }
            }
        }
    }

    private suspend fun callBreedListApi() {
        when (val results = fetchBreedListUseCase.invoke()) {
            is Results.Success -> {
                breedList = results.response
                reduceState {
                    BreedListState.BreedListContentState(listBreeds = breedList)
                }
            }
            is Results.Error -> {
                postEffect(BreedListEffect.ShowError(results.message))
            }
        }
    }
}