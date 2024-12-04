package com.example.skillcinemaapp.presentation.films_local

sealed interface FilmsLocalIntent {
    object Load : FilmsLocalIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : FilmsLocalIntent
    data class ClearHistory(val collectionId: Int) : FilmsLocalIntent
}