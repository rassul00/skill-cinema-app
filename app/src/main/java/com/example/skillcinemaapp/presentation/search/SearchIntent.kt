package com.example.skillcinemaapp.presentation.search

sealed interface SearchIntent {
    data object Clear : SearchIntent
    data class Load(val keyword: String) : SearchIntent
    data class NavigateToFilmDetail(val navigateToFilmDetail: () -> Unit) : SearchIntent
    data class NavigateToFilter(val navigateToFilter: () -> Unit) : SearchIntent
}