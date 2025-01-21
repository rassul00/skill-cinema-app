package com.example.skillcinemaapp.data.repository

import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.FilmDetailImage
import com.example.skillcinemaapp.data.model.FilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.model.FilterCountry
import com.example.skillcinemaapp.data.model.FilterGenre
import com.example.skillcinemaapp.data.model.SearchFilm
import com.example.skillcinemaapp.data.model.Staff
import com.example.skillcinemaapp.data.network.ApiService
import com.example.skillcinemaapp.data.room.Collection
import com.example.skillcinemaapp.data.room.CollectionDao
import com.example.skillcinemaapp.data.room.FilmCollection
import com.example.skillcinemaapp.data.room.LocalFilm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService, private val collectionDao: CollectionDao) : Repository {

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

    override suspend fun getFilmsByKeyword(keyword: String) : List<SearchFilm>{
        return apiService.getFilmsByKeyword(keyword).films
    }

    override suspend fun getGenres() : List<FilterGenre>{
        return apiService.getGenresAndCountries().genres
    }
    override suspend fun getCountries() : List<FilterCountry>{
        return apiService.getGenresAndCountries().countries
    }

    override suspend fun getFilmsByFilter(countries: String,
                                          genres: String,
                                          order: String,
                                          type: String,
                                          ratingFrom: String,
                                          ratingTo: String,
                                          yearFrom : String,
                                          yearTo: String): List<Film> {
        return apiService.getFilmsByFilter(
            countries=countries,
            genres=genres,
            order=order,
            type=type,
            ratingFrom=ratingFrom,
            ratingTo=ratingTo,
            yearFrom=yearFrom,
            yearTo=yearTo
        ).items
    }







    override suspend fun getCollectionIdByName(name: String): Int {
        return collectionDao.getCollectionIdByName(name)
    }

    override suspend fun addCollection(collection: Collection) {
        collectionDao.addCollection(collection)
    }

    override fun getCollections(): Flow<List<Collection>> {
        return collectionDao.getCollections()
    }

    override suspend fun addFilm(collectionId: Int, film: Film) {

        collectionDao.addToFilmCollection(FilmCollection(filmId = film.id, collectionId = collectionId))
        collectionDao.addFilm(
            LocalFilm(
                id = film.id,
                poster = film.poster,
                nameOriginal = film.nameOriginal,
                name = film.name,
                cover = film.cover,
                genre = film.genres[0].genre,
                country = film.countries[0].country,
                rating = film.rating,
                shorDes = film.shorDes,
                fullDes = film.fullDes,
                year = film.year,
                ageLimit = film.ageLimit

            )
        )

    }

    override fun getFilmsByCollectionId(collectionId: Int): Flow<List<LocalFilm>> {
        return collectionDao.getFilmsByCollectionId(collectionId)
    }

    override suspend fun clearCollection(collectionId: Int) {
        collectionDao.clearCollection(collectionId = collectionId)
    }

    override suspend fun increaseCollectionSize(collectionId: Int) {
        collectionDao.increaseCollectionSize(collectionId)
    }

    override suspend fun decreaseCollectionSize(collectionId: Int) {
        collectionDao.decreaseCollectionSize(collectionId)
    }

    override suspend fun deleteFilmFromCollection(filmId: Int, collectionId: Int) {
        collectionDao.deleteFilmFromCollection(filmId = filmId, collectionId = collectionId)
    }

    override suspend fun deleteFilmsFromCollection(collectionId: Int) {
        collectionDao.deleteFilmsFromCollection(collectionId = collectionId)
    }

    override suspend fun removeCollectionAndFilms(collectionId: Int) {
        collectionDao.removeCollectionAndFilms(collectionId = collectionId)
    }

    override suspend fun isFilmInCollection(filmId: Int, collectionId: Int): Boolean {
        return collectionDao.isFilmInCollection(filmId = filmId, collectionId = collectionId)
    }

    override suspend fun getCollectionNameById(collectionId: Int): String {
        return collectionDao.getCollectionNameById(collectionId = collectionId)
    }

}