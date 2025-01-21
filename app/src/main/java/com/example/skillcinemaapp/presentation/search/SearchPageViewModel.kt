package com.example.skillcinemaapp.presentation.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SearchPageViewModel @Inject constructor(val repository : RepositoryImpl) : ViewModel() {


    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        onEvent(SearchIntent.Load)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onEvent(event: SearchIntent) {
        when (event) {
            is SearchIntent.Clear -> {
                _searchUiState.value = SearchUiState.Initial
            }
            is SearchIntent.Load ->
                get()
            is SearchIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
            is SearchIntent.NavigateToFilter -> {
                event.navigateToFilter()
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun get() {

        viewModelScope.launch {
            try {
                _searchQuery
                    .debounce(500)
                    .collectLatest { keyword ->
                        if (keyword.isNotEmpty()) {
                            val films = repository.getFilmsByKeyword(keyword)

                            _searchUiState.value = SearchUiState.Success(films = films)
                        } else {
                            _searchUiState.value = SearchUiState.Initial
                        }
                    }


            } catch (_: IOException) {
                _searchUiState.value = SearchUiState.Error
            } catch (_: HttpException) {
                _searchUiState.value = SearchUiState.Error
            }
        }
    }

}