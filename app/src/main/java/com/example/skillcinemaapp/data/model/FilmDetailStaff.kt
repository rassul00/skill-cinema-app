package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetailStaff (
    @SerialName("staffId")
    val id : Int,
    @SerialName("posterUrl")
    val poster: String,
    @SerialName("nameRu")
    val name: String? = null,
    @SerialName("nameEn")
    val nameEn: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("professionText")
    val profession: String,
    @SerialName("professionKey")
    val professionKey: String
)

