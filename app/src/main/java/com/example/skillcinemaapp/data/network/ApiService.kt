package com.example.skillcinemaapp.data.network


import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.ResponseFilm
import com.example.skillcinemaapp.data.model.ResponseFilmDetailImage
import com.example.skillcinemaapp.data.model.ResponseFilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.model.ResponseGenreAndCountry
import com.example.skillcinemaapp.data.model.ResponseSearchFilm
import com.example.skillcinemaapp.data.model.Staff

import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
//const val API_KEY = "68070a5f-ae6b-49ca-b200-440015fb8b3f"
const val API_KEY = "be4dd998-de12-437e-8693-d6be4002ed9e"
//const val API_KEY = "16e3abeb-1d11-4818-a0f0-e113af2c8874"
//const val API_KEY = "64b7de10-e029-4d26-b33f-57f450ef348b"
//const val API_KEY = "73c604d2-a930-425a-809c-f1c9fe5da228"

interface ApiService {


    @GET("api/v2.2/films/collections")
    suspend fun getFilmsByCategory(
        @Query("type") category: String
    ): ResponseFilm



    @GET("api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("id") id: String
    ): Film


    @GET("api/v1/staff")
    suspend fun getStaffOfFilm(
        @Query("filmId") id: String
    ): List<FilmDetailStaff>


    @GET("api/v1/staff/{id}")
    suspend fun getStaffById(
        @Path("id")id: String
    ): Staff

    @GET("/api/v2.2/films/{id}/images")
    suspend fun getImagesOfFilm(
        @Path("id")id: String
    ): ResponseFilmDetailImage

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id")id: String
    ): ResponseFilmDetailSimilarFilm


    @GET("api/v2.1/films/search-by-keyword")
    suspend fun getFilmsByKeyword(
        @Query("keyword")keyword: String
    ): ResponseSearchFilm

    @GET("api/v2.2/films/filters")
    suspend fun getGenresAndCountries(
    ): ResponseGenreAndCountry

    @GET("api/v2.2/films")
    suspend fun getFilmsByFilter(
        @Query("countries")countries: String,
        @Query("genres")genres: String,
        @Query("order")order: String,
        @Query("type")type: String,
        @Query("ratingFrom")ratingFrom: String,
        @Query("ratingTo")ratingTo: String,
        @Query("yearFrom")yearFrom: String,
        @Query("yearTo")yearTo: String,
    ): ResponseFilm

}
