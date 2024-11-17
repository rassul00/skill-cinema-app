package com.example.skillcinemaapp.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ResponseFilm(
    val total: Int,
    val totalPages: Int,
    val items: List<Film>
)