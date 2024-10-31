package com.example.skillcinemaapp.domain.network


import com.example.skillcinemaapp.data.Response
import retrofit2.Retrofit
import retrofit2.http.GET

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Headers

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
val json = Json { ignoreUnknownKeys = true }

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

interface ApiService {

    @Headers(
        "X-API-KEY: 68070a5f-ae6b-49ca-b200-440015fb8b3f",
        "Content-Type: application/json"
    )
    @GET("api/v2.2/films/collections?type=TOP_250_MOVIES&page=1")
    suspend fun getTop250Films(): Response

    @Headers(
        "X-API-KEY: 68070a5f-ae6b-49ca-b200-440015fb8b3f",
        "Content-Type: application/json"
    )
    @GET("api/v2.2/films/collections?type=TOP_POPULAR_MOVIES&page=1")
    suspend fun getPopularFilms(): Response


    @Headers(
        "X-API-KEY: 68070a5f-ae6b-49ca-b200-440015fb8b3f",
        "Content-Type: application/json"
    )
    @GET("api/v2.2/films/collections?type=COMICS_THEME&page=1")
    suspend fun getComicsFilms(): Response

    @Headers(
        "X-API-KEY: 68070a5f-ae6b-49ca-b200-440015fb8b3f",
        "Content-Type: application/json"
    )
    @GET("api/v2.2/films/collections?type=TOP_250_TV_SHOWS&page=1")
    suspend fun getSeries(): Response


}


object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}