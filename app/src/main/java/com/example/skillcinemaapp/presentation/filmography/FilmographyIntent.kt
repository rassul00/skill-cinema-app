package com.example.skillcinemaapp.presentation.filmography


sealed interface FilmographyIntent {
    object Load : FilmographyIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : FilmographyIntent
    data class NavigateToFilmDetail(val navigateToFilmDetail: () -> Unit) : FilmographyIntent
}