package com.example.skillcinemaapp.presentation.list_film



sealed interface ListFilmIntent {
    object Load : ListFilmIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : ListFilmIntent
    data class NavigateToFilmDetail( val navigateToFilmDetail: () -> Unit) : ListFilmIntent
}