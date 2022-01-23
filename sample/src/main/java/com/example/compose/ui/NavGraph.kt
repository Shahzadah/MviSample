package com.example.compose.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import com.example.compose.data.model.BreedDetails
import com.example.compose.ui.breedDetails.BreedDetailsScreen
import com.example.compose.ui.breedSearch.BreedListScreen
import com.example.compose.utils.ToolbarDetails
import com.example.compose.utils.TopBar
import com.google.gson.Gson

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    var toolBar by rememberSaveable { mutableStateOf(ToolbarDetails()) }
    val navigationActions = NavigationActions(navController)
    Scaffold(topBar = { TopBar(toolBar, navController) }) {
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

sealed class Screen(val route: String) {
    object BreedList : Screen("BreedListScreen")
    object BreedDetails : Screen("BreedDetailsScreen")
}