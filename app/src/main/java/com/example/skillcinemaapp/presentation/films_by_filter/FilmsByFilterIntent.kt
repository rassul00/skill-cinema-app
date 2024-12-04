package com.example.skillcinemaapp.presentation.films_by_filter

sealed interface FilmsByFilterIntent {
    object Load : FilmsByFilterIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : FilmsByFilterIntent
    data class NavigateToFilmDetail( val navigateToFilmDetail: () -> Unit) : FilmsByFilterIntent
}