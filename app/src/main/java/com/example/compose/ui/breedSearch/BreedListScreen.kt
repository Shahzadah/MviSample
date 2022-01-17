package com.example.compose.ui.breedSearch

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.data.model.BreedDetails
import com.example.compose.ui.breedSearch.mvi.BreedListEffect
import com.example.compose.ui.breedSearch.mvi.BreedListIntent
import com.example.compose.ui.breedSearch.mvi.BreedListState
import com.example.core_mvi.MviComposable

@Composable
fun BreedListScreen(launchBreedDetails: (breed: BreedDetails) -> Unit) {
    val context = LocalContext.current
    MviComposable(
        viewModel = hiltViewModel<BreedListViewModel>(),
        emitEffect = {
            when (it) {
                is BreedListEffect.LaunchBreedDetailsScreen -> launchBreedDetails.invoke(it.breedDetails)
                is BreedListEffect.ShowError -> Toast.makeText(context, it.error, Toast.LENGTH_LONG)
                    .show()
            }
        }
    ) { state, sendIntent ->
        if (state.current == BreedListState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
        if (state.current is BreedListState.Information) {
            var breedName by rememberSaveable { mutableStateOf("") }
            Column {
                SearchView(breedName) {
                    breedName = it
                }
                Divider()
                BreedList(state.current.listBreeds, breedName) {
                    sendIntent.invoke(BreedListIntent.BreedsListItemClicked(it))
                }
            }
        }
    }
}

@Composable
fun BreedList(
    listBreedDetails: List<BreedDetails>,
    searchText: String,
    launchBreedDetails: (breed: BreedDetails) -> Unit
) {
    val filteredList = if (searchText.isEmpty()) {
        listBreedDetails
    } else {
        val tempList = ArrayList<BreedDetails>()
        listBreedDetails.forEach {
            if (it.name.contains(searchText, ignoreCase = true)) {
                tempList.add(it)
            }
        }
        tempList
    }
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(filteredList) { breedItem ->
            BreedListItem(breedItem) {
                launchBreedDetails(breedItem)
            }
        }
    }
}