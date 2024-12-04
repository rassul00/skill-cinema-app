package com.example.skillcinemaapp.presentation.films_local


import com.example.skillcinemaapp.data.room.LocalFilm

sealed interface FilmsLocalUiState {
    object Loading : FilmsLocalUiState
    data class Success(val collectionId: Int, val collectionName: String, val films : List<LocalFilm>) : FilmsLocalUiState
    object Error : FilmsLocalUiState
}