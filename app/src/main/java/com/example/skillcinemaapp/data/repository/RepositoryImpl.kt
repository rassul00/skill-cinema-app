package com.example.skillcinemaapp.data.repository

import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.FilmDetailImage
import com.example.skillcinemaapp.data.model.Staff
import com.example.skillcinemaapp.data.model.FilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.network.ApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    override suspend fun getFilmsByCategory(category: String): List<Film>{
        return apiService.getFilmsByCategory(category).items
    }

    override suspend fun getFilmById(id: String) : Film{
        return apiService.getFilmById(id)
    }

    override suspend fun getStaffOfFilm(id: String) : List<FilmDetailStaff>{
        return apiService.getStaffOfFilm(id)
    }

    override suspend fun getStaffById(id: String) : Staff{
        return apiService.getStaffById(id)
    }


    override suspend fun getImagesOfFilm(id: String) : List<FilmDetailImage>{
        return apiService.getImagesOfFilm(id).items
    }

    override suspend fun getSimilarFilms(id: String) : List<FilmDetailSimilarFilm>{
        return apiService.getSimilarFilms(id).items
    }

}