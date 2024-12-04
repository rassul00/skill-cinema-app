package com.example.skillcinemaapp.presentation.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SearchPageViewModel @Inject constructor(val repository : RepositoryImpl) : ViewModel() {


    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState


    private var searchJob: Job? = null


    fun onEvent(event: SearchIntent) {
        when (event) {
            is SearchIntent.Clear -> {
                _searchUiState.value = SearchUiState.Initial
            }
            is SearchIntent.Load ->
                get(event.keyword)
            is SearchIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
            is SearchIntent.NavigateToFilter -> {
                event.navigateToFilter()
            }
        }
    }

    private fun get(keyword : String) {

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _searchUiState.value = SearchUiState.Initial
            _searchUiState.value = try {

                val films = repository.getFilmsByKeyword(keyword)

                SearchUiState.Success(films = films)

            } catch (_: IOException) {
                SearchUiState.Error
            } catch (_: HttpException) {
                SearchUiState.Error
            }
        }
    }

}