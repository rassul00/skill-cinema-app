package com.example.skillcinemaapp.presentation.profile

import com.example.skillcinemaapp.data.room.Collection
import com.example.skillcinemaapp.data.room.LocalFilm

sealed interface ProfileUiState {
    object Initial : ProfileUiState
    data class Success(val films: List<LocalFilm>, val collections : List<Collection>) : ProfileUiState
    object Error : ProfileUiState
}