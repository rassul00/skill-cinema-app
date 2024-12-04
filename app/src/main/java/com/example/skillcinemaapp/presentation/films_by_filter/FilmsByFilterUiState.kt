package com.example.skillcinemaapp.presentation.films_by_filter

import com.example.skillcinemaapp.data.model.Film

sealed interface FilmsByFilterUiState {
    object Loading : FilmsByFilterUiState
    data class Success(val films : List<Film>) : FilmsByFilterUiState
    object Error : FilmsByFilterUiState
}