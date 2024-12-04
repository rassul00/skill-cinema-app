package com.example.skillcinemaapp.presentation.film_detail

import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.FilmDetailImage
import com.example.skillcinemaapp.data.model.FilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.room.Collection

sealed interface FilmDetailUiState {
    object Loading : FilmDetailUiState
    data class Success(val film : Film, val filmDetailStaffs: List<FilmDetailStaff>, val images: List<FilmDetailImage>, val filmDetailSimilarFilms: List<FilmDetailSimilarFilm>, val collections : List<Collection>) : FilmDetailUiState
    object Error : FilmDetailUiState
}