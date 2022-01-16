package com.example.compose.ui.breedDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.example.compose.data.model.BreedDetails
import com.example.compose.data.model.BreedDetailsProvider

@Composable
fun BreedDetailsScreen(breedDetails: BreedDetails) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .padding(bottom = 16.dp),
    ) {
        breedDetails.image?.let { it ->
            Image(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.5f)
                    .fillMaxWidth(),
                painter = rememberImagePainter(it.url, builder = {
                    size(OriginalSize)
                }),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
        breedDetails.temperament?.let {
            Text(
                it,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Text(breedDetails.weight.metric + " kgs")
        Text(breedDetails.height.metric + " cm at the withers")
        Text(breedDetails.lifeSpan + " average lifespan")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBreedDetailsScreen(@PreviewParameter(BreedDetailsProvider::class) breedItem: BreedDetails) {
    BreedDetailsScreen(breedItem)
}