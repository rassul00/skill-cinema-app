package com.example.skillcinemaapp.presentation.films_by_filter

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class FilmsByFilterPageViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {



    private val _filmsByFilterUiState = MutableStateFlow<FilmsByFilterUiState>(FilmsByFilterUiState.Loading)
    val filmsByFilterUiState: StateFlow<FilmsByFilterUiState> = _filmsByFilterUiState


    var country = savedStateHandle.get<String>("country") ?: "Россия"

    var countryId = savedStateHandle.get<String>("countryId") ?: "34"

    var genre = savedStateHandle.get<String>("genre") ?: "комедия"

    var genreId = savedStateHandle.get<String>("genreId") ?: "13"

    var order = savedStateHandle.get<String>("order") ?: "RATING"

    var type = savedStateHandle.get<String>("type") ?: "ALL"

    var ratingFrom = savedStateHandle.get<String>("ratingFrom") ?: "0"

    var ratingTo = savedStateHandle.get<String>("ratingTo") ?: "10"

    var fromPer = savedStateHandle.get<String>("fromPer") ?: "1998"

    var toPer = savedStateHandle.get<String>("toPer") ?: "2017"




    init {
        onEvent(FilmsByFilterIntent.Load)
    }


    fun onEvent(event: FilmsByFilterIntent) {
        when (event) {
            is FilmsByFilterIntent.Load ->
                get()
            is FilmsByFilterIntent.NavigateToBack -> {
                event.navigateToBack()
            }
            is FilmsByFilterIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
        }
    }

    private fun get() {
        viewModelScope.launch {
            _filmsByFilterUiState.value = FilmsByFilterUiState.Loading
            _filmsByFilterUiState.value = try {

                val films = repository.getFilmsByFilter(
                    countries = countryId,
                    genres = genreId,
                    order = order,
                    type = type,
                    ratingFrom = ratingFrom,
                    ratingTo = ratingTo,
                    yearFrom = fromPer,
                    yearTo = toPer
                )
                Log.d("Filter", "Filter: $films")

                FilmsByFilterUiState.Success(films = films)


            } catch (e: IOException) {
                Log.e("Filter", "IOException: ${e.message}")
                FilmsByFilterUiState.Error
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("Filter", "HttpException: ${e.message}, Error Body: $errorBody")
                FilmsByFilterUiState.Error
            }
        }
    }

}