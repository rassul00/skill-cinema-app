package com.example.skillcinemaapp.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ResponseFilmDetailSimilarFilm(
    val total: Int,
    val items: List<FilmDetailSimilarFilm>
)