package com.example.skillcinemaapp.presentation.list_film

import com.example.skillcinemaapp.data.model.Film

sealed interface ListFilmUiState {
    object Loading : ListFilmUiState
    data class Success(val films : List<Film>) : ListFilmUiState
    object Error : ListFilmUiState
}