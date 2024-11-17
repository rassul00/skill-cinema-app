package com.example.skillcinemaapp.presentation.film_detail




sealed interface FilmDetailIntent {
    object Load : FilmDetailIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : FilmDetailIntent
    data class NavigateToGallery(val navigateToGallery: () -> Unit) : FilmDetailIntent
    data class NavigateToStaff(val navigateToStaff: () -> Unit) : FilmDetailIntent
}