package com.example.skillcinemaapp.presentation.country


sealed interface CountryIntent {
    object Load : CountryIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : CountryIntent
}