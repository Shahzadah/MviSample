package com.example.compose.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class BreedDetails(
    val id: Int,
    val name: String,
    val temperament: String?,
    val image: Image?,
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