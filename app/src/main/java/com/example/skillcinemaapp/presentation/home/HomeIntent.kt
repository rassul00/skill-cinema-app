package com.example.skillcinemaapp.presentation.home

sealed interface HomeIntent {
    object Load : HomeIntent
    data class NavigateToListFilm(val navigateToListFilm: () -> Unit) : HomeIntent
    data class NavigateToFilmDetail(val navigateToFilmDetail: () -> Unit) : HomeIntent
}