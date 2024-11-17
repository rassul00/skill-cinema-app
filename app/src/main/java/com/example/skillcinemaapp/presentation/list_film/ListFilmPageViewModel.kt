package com.example.skillcinemaapp.presentation.list_film

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ListFilmPageViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {


    private val _listFilmUiState = MutableStateFlow<ListFilmUiState>(ListFilmUiState.Loading)
    val listFilmUiState: StateFlow<ListFilmUiState> = _listFilmUiState

    var category = savedStateHandle.get<String>("category") ?: ""


    private val categoryMap: Map<String, suspend () -> List<Film>> = mapOf(
        "Топ-250" to { repository.getFilmsByCategory("TOP_250_MOVIES") },
        "Популярное" to { repository.getFilmsByCategory("TOP_POPULAR_MOVIES") },
        "Фильмы о комиксах" to { repository.getFilmsByCategory("COMICS_THEME") },
        "Сериалы" to { repository.getFilmsByCategory("TOP_250_TV_SHOWS") }
    )



    init {
        onEvent(ListFilmIntent.Load)
    }
    fun onEvent(event: ListFilmIntent) {
        when (event) {
            is ListFilmIntent.Load ->
                get()
            is ListFilmIntent.NavigateToBack -> {
                event.navigateToBack()
            }
            is ListFilmIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
        }
    }

    private fun get() {
        viewModelScope.launch {
            _listFilmUiState.value = ListFilmUiState.Loading
            _listFilmUiState.value = try {
                val films = categoryMap[category]?.invoke() ?: emptyList()
                ListFilmUiState.Success(films = films)
            } catch (_: IOException) {
                ListFilmUiState.Error
            } catch (_: HttpException) {
                ListFilmUiState.Error
            }
        }
    }

}