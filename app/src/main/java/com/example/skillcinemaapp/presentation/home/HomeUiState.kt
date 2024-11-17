package com.example.skillcinemaapp.presentation.home

import com.example.skillcinemaapp.data.model.Film

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val films : List<List<Film>>) : HomeUiState
    object Error : HomeUiState
}