package com.example.skillcinemaapp.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.skillcinemaapp.data.Film
import com.example.skillcinemaapp.domain.network.Api
import retrofit2.HttpException
import java.io.IOException

sealed interface UiState {
    object Initial : UiState
    data class Success(val films : List<List<Film>>) : UiState
    object Error : UiState
    object Loading : UiState
}

class HomePageViewModel : ViewModel() {
    var uiState by mutableStateOf<UiState>(UiState.Initial)
        private set



    private var film : List<Film> = emptyList()

    fun setFilms(film: List<Film>){
        this.film = film
    }

    fun getFilm(): List<Film> {
        return film
    }

    init {
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {

                val top250Films = Api.retrofitService.getTop250Films()
                val popularFilms = Api.retrofitService.getPopularFilms()
                val comicsFilms = Api.retrofitService.getComicsFilms()
                val series = Api.retrofitService.getSeries()

//                Log.d("HomePageViewModel", "Fetched films: $top250Films")
//                Log.d("HomePageViewModel", "Fetched films: $popularFilms")
//                Log.d("HomePageViewModel", "Fetched films: $comicsFilms")
//                Log.d("HomePageViewModel", "Fetched films: $series")


                UiState.Success(
                    films = listOf(
                        top250Films.items,
                        popularFilms.items,
                        comicsFilms.items,
                        series.items
                    )
                )


            } catch (_: IOException) {
                UiState.Error
            } catch (_: HttpException) {
                UiState.Error
            }
        }
    }
}
