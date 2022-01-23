package com.example.compose.ui.breedSearch.domain

import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.Results
import com.example.compose.data.repository.BreedSearchRepository
import javax.inject.Inject

class FetchBreedListUseCase @Inject constructor(private val repository: BreedSearchRepository) {

    suspend fun invoke(): Results<List<BreedDetails>, String> {
        return repository.fetchBreedsList()
    }
}