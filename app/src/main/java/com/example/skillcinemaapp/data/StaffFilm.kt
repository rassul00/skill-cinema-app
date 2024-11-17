package com.example.skillcinemaapp.data.model

import com.example.skillcinemaapp.data.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StaffFilm(
    @SerialName("filmId")
    val id: Int,
    var poster: String? = null,
    @SerialName("nameEn")
    val nameOriginal: String? = null,
    @SerialName("nameRu")
    val name: String? = null,
    var genres: List<Genre> = emptyList(),
    var rating: Double? = null,
    @SerialName("professionKey")
    val profession: String
)
