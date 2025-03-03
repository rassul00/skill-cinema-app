package com.example.skillcinemaapp.presentation.gallery

import com.example.skillcinemaapp.data.model.FilmDetailImage


sealed interface GalleryUiState {
    object Loading : GalleryUiState
    data class Success(val images: List<FilmDetailImage>) : GalleryUiState
    object Error : GalleryUiState
}