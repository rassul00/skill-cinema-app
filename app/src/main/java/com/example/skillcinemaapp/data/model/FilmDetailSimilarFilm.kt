package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetailSimilarFilm(
    @SerialName("filmId")
    val id: Int,
    var poster: String? = null,
    @SerialName("nameOriginal")
    val nameOriginal: String? = null,
    @SerialName("nameRu")
    val name: String? = null,
    var genres: List<Genre> = emptyList(),
    var rating: Double? = null
)

