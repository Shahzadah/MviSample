package com.example.compose.ui

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import com.example.compose.data.model.BreedDetails
import com.example.compose.ui.breedDetails.BreedDetailsScreen
import com.example.compose.ui.breedSearch.BreedListScreen
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    var toolBar by rememberSaveable { mutableStateOf(ToolbarDetails()) }
    val navigationActions = NavigationActions(navController)
    Scaffold(topBar = {
        toolBar.title?.let {
            TopAppBar(
                title = {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    if (toolBar.isBackNeeded) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                actions = {
                    Box(modifier = Modifier.width(70.dp))
                },
                elevation = 12.dp
            )
        }
    }) {
        NavHost(
            navController = navController,
            startDestination = Screen.BreedList.route
        ) {
            composable(Screen.BreedList.route) {
                toolBar = ToolbarDetails(title = stringResource(id = R.string.search_dog_breeds))
                BreedListScreen(launchBreedDetails = navigationActions.launchBreedDetails)
            }
            composable(route = Screen.BreedDetails.route.plus("/{BreedDetailsData}")) {
                it.arguments?.getString("BreedDetailsData")?.let { json ->
                    val breedDetails = Gson().fromJson(json, BreedDetails::class.java)
                    toolBar = ToolbarDetails(title = breedDetails.name, isBackNeeded = true)
                    BreedDetailsScreen(breedDetails)
                }
            }
        }
    }
}

@Parcelize
@Keep
data class ToolbarDetails(
    val title: String? = null,
    val isBackNeeded: Boolean = false,
) : Parcelable

sealed class Screen(val route: String) {
    object BreedList : Screen("BreedListScreen")
    object BreedDetails : Screen("BreedDetailsScreen")
}