package com.example.skillcinemaapp.presentation.genre


sealed interface GenreIntent {
    object Load : GenreIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : GenreIntent
}