package com.example.skillcinemaapp.presentation.staff


sealed interface StaffIntent {
    object Load : StaffIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : StaffIntent
    data class NavigateToFilmDetail(val navigateToFilmDetail: () -> Unit) : StaffIntent
    data class NavigateToFilmography(val navigateToFilmography: () -> Unit) : StaffIntent
}