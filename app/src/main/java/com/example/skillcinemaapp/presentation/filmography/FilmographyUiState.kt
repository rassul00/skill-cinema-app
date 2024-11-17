package com.example.skillcinemaapp.presentation.filmography

import com.example.skillcinemaapp.data.model.Staff

sealed interface FilmographyUiState {
    data object Loading : FilmographyUiState
    data class Success(val staff: Staff) : FilmographyUiState
    data object Error : FilmographyUiState
}