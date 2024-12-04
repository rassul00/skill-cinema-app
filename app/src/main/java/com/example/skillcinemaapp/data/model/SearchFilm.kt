package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchFilm (
    @SerialName("filmId")
    val id: Int,
    @SerialName("posterUrl")
    var poster: String? = null,
    @SerialName("nameOriginal")
    val nameOriginal: String? = null,
    @SerialName("nameRu")
    val name: String? = null,
    @SerialName("genres")
    var genres: List<Genre> = emptyList(),
    @SerialName("rating")
    var rating: String? = null
)