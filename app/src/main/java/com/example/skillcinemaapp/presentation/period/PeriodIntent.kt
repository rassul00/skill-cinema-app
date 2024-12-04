package com.example.skillcinemaapp.presentation.period

sealed interface PeriodIntent {
    data class NavigateToBack(val navigateToBack: () -> Unit) : PeriodIntent
}