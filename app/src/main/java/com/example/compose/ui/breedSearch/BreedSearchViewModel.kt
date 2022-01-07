package com.example.compose.ui.breedSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.repository.BreedSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedSearchViewModel @Inject constructor(private val repository: BreedSearchRepository) :
    ViewModel() {

    init {
        viewModelScope.launch {
            callBreedListApi()
        }
    }

    fun test() {
        viewModelScope.launch {
            callBreedListApi()
        }
    }

    private suspend fun callBreedListApi() {
        repository.fetchBreedsList { }
    }
}