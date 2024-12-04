package com.example.skillcinemaapp.presentation.genre

import com.example.skillcinemaapp.data.model.FilterGenre

sealed interface GenreUiState {
    object Loading : GenreUiState
    data class Success(val genres : List<FilterGenre>) : GenreUiState
    object Error : GenreUiState
}