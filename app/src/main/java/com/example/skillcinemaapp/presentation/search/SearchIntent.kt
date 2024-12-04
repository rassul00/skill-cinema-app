package com.example.skillcinemaapp.presentation.search

sealed interface SearchIntent {
    object Clear : SearchIntent
    object Load : SearchIntent
    data class NavigateToFilmDetail(val navigateToFilmDetail: () -> Unit) : SearchIntent
    data class NavigateToFilter(val navigateToFilter: () -> Unit) : SearchIntent
}