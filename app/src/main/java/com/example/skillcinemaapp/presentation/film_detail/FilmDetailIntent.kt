package com.example.skillcinemaapp.presentation.film_detail

import com.example.skillcinemaapp.data.model.Film


sealed interface FilmDetailIntent {
    object Load : FilmDetailIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : FilmDetailIntent
    data class NavigateToGallery(val navigateToGallery: () -> Unit) : FilmDetailIntent
    data class NavigateToStaff(val navigateToStaff: () -> Unit) : FilmDetailIntent
    data class SaveFilmToCollection(val collectionId: Int, val film: Film) : FilmDetailIntent
    data class AddNewCollection(val collectionName: String) : FilmDetailIntent
    data class DeleteFilmFromCollection(val collectionId: Int, val filmId: Int) : FilmDetailIntent

}