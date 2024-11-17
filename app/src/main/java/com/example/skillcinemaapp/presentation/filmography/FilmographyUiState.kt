package com.example.skillcinemaapp.presentation.filmography

import com.example.skillcinemaapp.data.model.Staff

sealed interface FilmographyUiState {
    object Loading : FilmographyUiState
    data class Success(val staff: Staff) : FilmographyUiState
    object Error : FilmographyUiState
}