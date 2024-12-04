package com.example.skillcinemaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResponseGenreAndCountry (
    val genres : List<FilterGenre>,
    val countries : List<FilterCountry>
)


@Serializable
data class FilterGenre(
    @SerialName("id")
    val id : Int,
    @SerialName("genre")
    val genre: String
)

@Serializable
data class FilterCountry(
    @SerialName("id")
    val id : Int,
    @SerialName("country")
    val country: String
)

