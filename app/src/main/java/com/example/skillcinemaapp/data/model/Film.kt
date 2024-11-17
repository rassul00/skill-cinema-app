package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Film(
    @SerialName("kinopoiskId")
    val id: Int,
    @SerialName("posterUrl")
    val poster: String,
    @SerialName("nameOriginal")
    val nameOriginal: String? = null,
    @SerialName("nameRu")
    val name: String? = null,
    @SerialName("coverUrl")
    val cover: String? = null,
    val genres: List<Genre>,
    val countries: List<Country>,
    @SerialName("ratingImdb")
    val rating: Double? = null,
    @SerialName("shortDescription")
    val shorDes: String? = null,
    @SerialName("description")
    val fullDes: String? = null,
    @SerialName("year")
    val year: Int? = null,
    @SerialName("ratingAgeLimits")
    val ageLimit : String? = null

)


@Serializable
data class Genre(
    @SerialName("genre")
    val genre: String
)

@Serializable
data class Country(
    val country: String
)
