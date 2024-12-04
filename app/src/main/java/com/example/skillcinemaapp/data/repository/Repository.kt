package com.example.skillcinemaapp.data.repository

import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.FilmDetailImage
import com.example.skillcinemaapp.data.model.FilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.model.Staff

interface Repository {

    suspend fun getFilmsByCategory(category: String): List<Film>

    suspend fun getFilmById(id: String): Film

    suspend fun getStaffOfFilm(id: String): List<FilmDetailStaff>

    suspend fun getStaffById(id: String): Staff

    suspend fun getImagesOfFilm(id: String): List<FilmDetailImage>

    suspend fun getSimilarFilms(id: String): List<FilmDetailSimilarFilm>
}