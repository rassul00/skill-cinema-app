package com.example.skillcinemaapp.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseFilmDetailImage(
    val total: Int,
    val totalPages: Int,
    val items: List<FilmDetailImage>
)