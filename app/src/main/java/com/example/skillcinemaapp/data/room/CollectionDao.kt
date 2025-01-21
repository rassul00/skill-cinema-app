package com.example.skillcinemaapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollection(collection: Collection)

    @Query("SELECT id FROM collections WHERE name = :name LIMIT 1")
    suspend fun getCollectionIdByName(name: String): Int

    @Query("SELECT name FROM collections WHERE id = :collectionId LIMIT 1")
    suspend fun getCollectionNameById(collectionId: Int): String

    @Query("SELECT * FROM collections")
    fun getCollections(): Flow<List<Collection>>

    @Query("UPDATE collections SET collectionSize = 0 WHERE id = :collectionId")
    suspend fun clearCollection(collectionId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilm(localFilm: LocalFilm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFilmCollection(filmCollection: FilmCollection)

    @Query("SELECT * FROM local_films WHERE id IN (SELECT filmId FROM film_collection WHERE collectionId = :collectionId)")
    fun getFilmsByCollectionId(collectionId: Int): Flow<List<LocalFilm>>

    @Query("UPDATE collections SET collectionSize = collectionSize + 1 WHERE id = :collectionId")
    suspend fun increaseCollectionSize(collectionId: Int)

    @Query("UPDATE collections SET collectionSize = collectionSize - 1 WHERE id = :collectionId")
    suspend fun decreaseCollectionSize(collectionId: Int)

    @Query("DELETE FROM film_collection WHERE filmId = :filmId AND collectionId = :collectionId")
    suspend fun deleteFilmFromCollection(filmId: Int, collectionId: Int)

    @Query("DELETE FROM film_collection WHERE collectionId = :collectionId")
    suspend fun deleteFilmsFromCollection(collectionId: Int)

    @Query("DELETE FROM collections WHERE id = :collectionId")
    suspend fun deleteCollection(collectionId: Int)

    suspend fun removeCollectionAndFilms(collectionId: Int) {
        deleteFilmsFromCollection(collectionId)
        deleteCollection(collectionId)
    }

    @Query("SELECT COUNT(*) > 0 FROM film_collection WHERE filmId = :filmId AND collectionId = :collectionId")
    suspend fun isFilmInCollection(filmId: Int, collectionId: Int): Boolean



}