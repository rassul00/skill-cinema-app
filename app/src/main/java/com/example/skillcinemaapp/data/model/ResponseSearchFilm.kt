package com.example.skillcinemaapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchFilm(
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int,
    val films : List<SearchFilm>
)