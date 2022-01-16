package com.example.compose.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class BreedDetails(
    val id: Int,
    val name: String,
    val temperament: String?,
    var image: Image?,
    val weight: Measurement,
    val height: Measurement,

    @SerializedName("life_span")
    val lifeSpan: String,
) : Parcelable

@Parcelize
@Keep
data class Image(
    val url: String
) : Parcelable

@Parcelize
@Keep
data class Measurement(val imperial: String, val metric: String) : Parcelable

class BreedDetailsProvider : PreviewParameterProvider<BreedDetails> {
    override val values: Sequence<BreedDetails> = sequenceOf(
        BreedDetails(
            id = 0,
            name = "Afghan Hound",
            weight = Measurement(imperial = "6 - 13", metric = "3 - 6"),
            height = Measurement(imperial = "9 - 11.5", metric = "23 - 29"),
            image = null,
            lifeSpan = "10-13 years",
            temperament = "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving"
        )
    )
    override val count: Int = values.count()
}