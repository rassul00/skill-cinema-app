package com.example.skillcinemaapp.presentation.search

import com.example.skillcinemaapp.data.model.SearchFilm

sealed interface SearchUiState {
    object Initial : SearchUiState
    data class Success(val films : List<SearchFilm>) : SearchUiState
    object Error : SearchUiState
}