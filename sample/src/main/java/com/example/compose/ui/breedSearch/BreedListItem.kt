package com.example.compose.ui.breedSearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.compose.R
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.BreedDetailsProvider

@Composable
fun BreedListItem(breedItem: BreedDetails, clickCallback: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = clickCallback),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(corner = CornerSize(6.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            breedItem.image?.let {
                Image(
                    modifier = Modifier
                        .size(width = 80.dp, height = 64.dp),
                    painter = rememberImagePainter(it.url),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Column() {
                Text(text = breedItem.name)
                Text(text = stringResource(R.string.average_lifespan).format(breedItem.lifeSpan))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBreedListItem(@PreviewParameter(BreedDetailsProvider::class) breedItem: BreedDetails) {
    breedItem.image = com.example.compose.data.model.Image(url = "")
    BreedListItem(breedItem)
}