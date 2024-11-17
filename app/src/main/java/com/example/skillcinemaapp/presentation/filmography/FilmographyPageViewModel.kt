package com.example.skillcinemaapp.presentation.filmography

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
class FilmographyPageViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _filmographyUiState = MutableStateFlow<FilmographyUiState>(FilmographyUiState.Loading)
    val filmographyUiState: StateFlow<FilmographyUiState> = _filmographyUiState


    var staffId = savedStateHandle.get<String>("staffId") ?: ""

    init{
        onEvent(FilmographyIntent.Load)
    }


    fun onEvent(event: FilmographyIntent){
        when (event) {
            is FilmographyIntent.Load -> get()
            is FilmographyIntent.NavigateToBack -> {
                event.navigateToBack()
            }
            is FilmographyIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
        }
    }


    private fun get() {
        viewModelScope.launch {
            _filmographyUiState.value = FilmographyUiState.Loading
            _filmographyUiState.value = try {

                val staff = repository.getStaffById(staffId)



                val maxFilms = 50
                for (index in 0 until minOf(staff.films.size, maxFilms)) {
                    val curr = staff.films[index]
                    val fullInfo = repository.getFilmById(curr.id.toString())
                    curr.poster = fullInfo.poster
                    curr.genres = fullInfo.genres
                    curr.rating = fullInfo.rating
                }

                FilmographyUiState.Success(
                    staff = staff
                )


            } catch (_: IOException) {
                FilmographyUiState.Error
            } catch (_: HttpException) {
                FilmographyUiState.Error
            }
        }
    }
}