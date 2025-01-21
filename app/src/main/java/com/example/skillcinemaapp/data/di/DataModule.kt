package com.example.skillcinemaapp.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skillcinemaapp.data.room.Collection
import com.example.skillcinemaapp.data.room.CollectionDao
import com.example.skillcinemaapp.data.room.CollectionDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CollectionDatabase {
        return Room.databaseBuilder(
            context,
            CollectionDatabase::class.java,
            "skill_cinema_app.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val database = Room.databaseBuilder(
                            context,
                            CollectionDatabase::class.java,
                            "skill_cinema_app.db"
                        ).build()
                        val collectionDao = database.collectionDao()
                        val defaultCollections = listOf(
                            Collection(name = "Просмотрено"),
                            Collection(name = "Любимое"),
                            Collection(name = "Хочу посмотреть")
                        )
                        defaultCollections.forEach { collectionDao.addCollection(it) }
                    }
                }
            })
            .build()
    }

    @Provides
    fun provideCollectionDao(database: CollectionDatabase): CollectionDao {
        return database.collectionDao()
    }
}
