package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Staff (
    @SerialName("personId")
    val id : Int,
    @SerialName("posterUrl")
    val poster: String,
    @SerialName("nameRu")
    val name: String? = null,
    @SerialName("nameEn")
    val nameEn: String? = null,
    @SerialName("profession")
    val profession: String,
    @SerialName("films")
    var films: List<StaffFilm>
)