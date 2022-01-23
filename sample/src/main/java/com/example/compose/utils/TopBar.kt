package com.example.compose.utils

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.R
import kotlinx.parcelize.Parcelize

@Composable
fun TopBar(toolbarDetails: ToolbarDetails, navController: NavHostController) {
    toolbarDetails.title?.let { toolbar ->
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            title = {
                Text(
                    text = toolbar,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            navigationIcon = {
                if (toolbarDetails.isBackNeeded) {
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
}

@Preview
@Composable
fun PreviewTopBarWithTitle() {
    TopBar(toolbarDetails = ToolbarDetails("Title"), navController = rememberNavController())
}

@Preview
@Composable
fun PreviewTopBarWithTitleWithBack() {
    TopBar(
        toolbarDetails = ToolbarDetails("Title", isBackNeeded = true),
        navController = rememberNavController()
    )
}

@Parcelize
@Keep
data class ToolbarDetails(
    val title: String? = null,
    val isBackNeeded: Boolean = false,
) : Parcelable