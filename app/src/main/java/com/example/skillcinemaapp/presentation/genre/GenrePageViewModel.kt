package com.example.skillcinemaapp.presentation.genre

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
class GenrePageViewModel @Inject constructor(val repository : RepositoryImpl) : ViewModel() {


    private val _genreUiState = MutableStateFlow<GenreUiState>(GenreUiState.Loading)
    val genreUiState: StateFlow<GenreUiState> = _genreUiState


    init {
        onEvent(
            GenreIntent.Load
        )
    }


    fun onEvent(event: GenreIntent) {
        when (event) {
            is GenreIntent.Load -> {
                get()
            }
            is GenreIntent.NavigateToBack -> {
                event.navigateToBack()
            }
        }

    }

    private fun get() {

        viewModelScope.launch {
            _genreUiState.value = GenreUiState.Loading
            _genreUiState.value = try {

                val genres = repository.getGenres()

                GenreUiState.Success(genres = genres)
            } catch (_: IOException) {
                GenreUiState.Error
            } catch (_: HttpException) {
                GenreUiState.Error
            }
        }
    }

}