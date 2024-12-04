package com.example.skillcinemaapp.presentation.search

import com.example.skillcinemaapp.data.model.SearchFilm

sealed interface SearchUiState {
    data object Initial : SearchUiState
    data class Success(val films : List<SearchFilm>) : SearchUiState
    data object Error : SearchUiState
}