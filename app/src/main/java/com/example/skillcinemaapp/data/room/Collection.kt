package com.example.skillcinemaapp.data.room


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val collectionSize: Int = 0
)

@Entity(tableName = "local_films")
data class LocalFilm(
    @PrimaryKey
    val id: Int,
    val poster: String? = null,
    val nameOriginal: String? = null,
    val name: String? = null,
    val cover: String? = null,
    val genre: String? = null,
    val country: String? = null,
    val rating: Double? = null,
    val shorDes: String? = null,
    val fullDes: String? = null,
    val year: Int? = null,
    val ageLimit : String? = null
)


@Entity(
    tableName = "film_collection"
)
data class FilmCollection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val filmId: Int,
    val collectionId: Int
)


