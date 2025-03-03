package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FilmDetailImage (
    @SerialName("imageUrl")
    val image : String,
    @SerialName("previewUrl")
    val preview : String
)