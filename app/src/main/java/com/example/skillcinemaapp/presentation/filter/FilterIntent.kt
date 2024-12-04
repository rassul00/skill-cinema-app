package com.example.skillcinemaapp.presentation.filter



sealed interface FilterIntent {
    data class NavigateToBack(val navigateToBack: () -> Unit) : FilterIntent

    data class NavigateToCountry(val navigateToCountry: () -> Unit) : FilterIntent
    data class NavigateToGenre(val navigateToGenre: () -> Unit) : FilterIntent
    data class NavigateToPeriod(val navigateToPeriod: () -> Unit) : FilterIntent
    data class NavigateToFilmsByFilter(val navigateToFilmsByFilter: () -> Unit) : FilterIntent

}