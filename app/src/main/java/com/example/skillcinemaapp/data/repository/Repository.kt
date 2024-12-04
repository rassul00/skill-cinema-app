package com.example.skillcinemaapp.data.repository

import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.FilmDetailImage
import com.example.skillcinemaapp.data.model.FilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.model.FilterCountry
import com.example.skillcinemaapp.data.model.FilterGenre
import com.example.skillcinemaapp.data.model.SearchFilm
import com.example.skillcinemaapp.data.model.Staff
import com.example.skillcinemaapp.data.room.Collection
import com.example.skillcinemaapp.data.room.LocalFilm
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getFilmsByCategory(category: String): List<Film>

    suspend fun getFilmById(id: String): Film

    suspend fun getStaffOfFilm(id: String): List<FilmDetailStaff>

    suspend fun getStaffById(id: String): Staff

    suspend fun getImagesOfFilm(id: String): List<FilmDetailImage>

    suspend fun getSimilarFilms(id: String): List<FilmDetailSimilarFilm>

    suspend fun getFilmsByKeyword(keyword: String): List<SearchFilm>

    suspend fun getGenres(): List<FilterGenre>

    suspend fun getCountries(): List<FilterCountry>

    suspend fun getFilmsByFilter(
        countries: String,
        genres: String,
        order: String,
        type: String,
        ratingFrom: String,
        ratingTo: String,
        yearFrom : String,
        yearTo: String
    ): List<Film>





    suspend fun getCollectionIdByName(name: String) : Int

    suspend fun getCollectionNameById(collectionId: Int) : String

    suspend fun clearCollection(collectionId: Int)

    suspend fun addCollection(collection: Collection)

    fun getCollections() : Flow<List<Collection>>

    suspend fun addFilm(collectionId: Int, film: Film)

    fun getFilmsByCollectionId(collectionId: Int): Flow<List<LocalFilm>>

    suspend fun increaseCollectionSize(collectionId: Int)

    suspend fun decreaseCollectionSize(collectionId: Int)

    suspend fun deleteFilmFromCollection(filmId: Int, collectionId: Int)

    suspend fun deleteFilmsFromCollection(collectionId: Int)

    suspend fun removeCollectionAndFilms(collectionId: Int)

    suspend fun isFilmInCollection(filmId: Int, collectionId: Int) : Boolean

}