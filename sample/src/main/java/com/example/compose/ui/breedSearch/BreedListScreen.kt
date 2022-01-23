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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.BreedDetailsProvider
import com.example.compose.data.model.Image
import com.example.compose.ui.breedSearch.mvi.BreedListEffect
import com.example.compose.ui.breedSearch.mvi.BreedListIntent
import com.example.core_mvi.MviComposable

@Composable
fun BreedListScreen(
    viewModel: BreedListViewModel,
    launchBreedDetails: (breed: BreedDetails) -> Unit
) {
    val context = LocalContext.current
    MviComposable(
        viewModel = viewModel,
        emitEffect = {
            when (it) {
                is BreedListEffect.LaunchBreedDetailsScreen -> launchBreedDetails.invoke(it.breedDetails)
                is BreedListEffect.ShowError -> Toast.makeText(context, it.error, Toast.LENGTH_LONG)
                    .show()
            }
        }
    ) { state, sendIntent ->
        if (state.loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
        state.filteredListBreeds?.let { filteredList ->
            Column {
                SearchView(state.searchText) {
                    sendIntent(BreedListIntent.SearchTextChanged(it))
                }
                Divider()
                BreedList(filteredList) {
                    sendIntent(BreedListIntent.BreedsListItemClicked(it))
                }
            }
        }
    }
}

@Composable
fun BreedList(
    listBreedDetails: List<BreedDetails>,
    launchBreedDetails: (breed: BreedDetails) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(listBreedDetails) { breedItem ->
            BreedListItem(breedItem) {
                launchBreedDetails(breedItem)
            }
        }
    }
}

@Preview
@Composable
fun PreviewBreedList(@PreviewParameter(BreedDetailsProvider::class) breedItem: BreedDetails) {
    breedItem.image = Image(url = "")
    BreedList(listOf(breedItem, breedItem, breedItem, breedItem)) {}
}