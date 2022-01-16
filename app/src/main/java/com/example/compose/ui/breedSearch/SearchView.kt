package com.example.compose.ui.breedSearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun SearchView(breedName: String, onChange: (String) -> Unit) {
    TextField(
        placeholder = { Text("Search by breed name") },
        value = breedName,
        onValueChange = { onChange(it) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RectangleShape,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (breedName.isNotEmpty()) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onChange("") }
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.DarkGray,
            cursorColor = Color.LightGray,
            leadingIconColor = Color.DarkGray,
            trailingIconColor = Color.DarkGray,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}