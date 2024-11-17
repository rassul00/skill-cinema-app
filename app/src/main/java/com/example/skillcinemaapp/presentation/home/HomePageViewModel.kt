package com.example.skillcinemaapp.presentation.home


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private val categories = listOf("TOP_250_MOVIES", "TOP_POPULAR_MOVIES", "COMICS_THEME", "TOP_250_TV_SHOWS")

    init {
        onEvent(HomeIntent.Load)
    }

    fun onEvent(event: HomeIntent) {
        when (event) {
            is HomeIntent.Load -> get()
            is HomeIntent.NavigateToListFilm -> {
                event.navigateToListFilm()
            }
            is HomeIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
        }
    }

    private fun get() {
        if (_homeUiState.value is HomeUiState.Success) return

        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Loading
            _homeUiState.value = try {
                val films = categories.map { category ->
                    repository.getFilmsByCategory(category)
                }
                HomeUiState.Success(films = films)
            } catch (_: IOException) {
                HomeUiState.Error
            } catch (_: HttpException) {
                HomeUiState.Error
            }
        }
    }

}


