package com.example.compose.ui

import android.net.Uri
import androidx.navigation.NavController
import com.example.compose.data.model.BreedDetails
import com.google.gson.Gson

class NavigationActions(navController: NavController) {
    val launchBreedDetails: (breedItem: BreedDetails) -> Unit = {
        val json = Uri.encode(Gson().toJson(it))
        navController.navigate("${Screen.BreedDetails.route}/${json}")
    }
}