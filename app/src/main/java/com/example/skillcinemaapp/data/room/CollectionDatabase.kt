package com.example.skillcinemaapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Collection::class, LocalFilm::class, FilmCollection::class], version = 1)
abstract class CollectionDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
}