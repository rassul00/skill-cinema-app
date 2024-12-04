package com.example.skillcinemaapp.presentation.films_local


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
class FilmsLocalPageViewModel @Inject constructor(val repository: RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {


    private val _filmsLocalUiState = MutableStateFlow<FilmsLocalUiState>(FilmsLocalUiState.Loading)
    val filmsLocalUiState: StateFlow<FilmsLocalUiState> = _filmsLocalUiState


    var collectionId = savedStateHandle.get<Int>("collectionId") ?: 0



    init {
        onEvent(FilmsLocalIntent.Load)
    }
    fun onEvent(event: FilmsLocalIntent) {
        when (event) {
            is FilmsLocalIntent.Load ->
                get()
            is FilmsLocalIntent.NavigateToBack -> {
                event.navigateToBack()
            }

            is FilmsLocalIntent.ClearHistory -> {
                viewModelScope.launch{


                    repository.clearCollection(event.collectionId)
                    repository.deleteFilmsFromCollection(event.collectionId)
                    repository.getFilmsByCollectionId(collectionId)
                        .collect{ films ->
                            (_filmsLocalUiState.value as? FilmsLocalUiState.Success)?.let {
                                _filmsLocalUiState.value = (it).copy(
                                    films = films
                                )
                            }

                        }
//                    val films = repository.getFilmsByCollectionId(collectionId = collectionId).first()
//
//                    (_filmsLocalUiState.value as? FilmsLocalUiState.Success)?.let {
//                        _filmsLocalUiState.value = (it).copy(
//                            films = films
//                        )
//                    }
                }
            }
        }
    }

    private fun get() {
        viewModelScope.launch {
            _filmsLocalUiState.value = FilmsLocalUiState.Loading
            try {

                val collectionName = repository.getCollectionNameById(collectionId)

                repository.getFilmsByCollectionId(collectionId)
                    .collect{ films ->
                        _filmsLocalUiState.value = FilmsLocalUiState.Success(collectionId = collectionId, collectionName = collectionName, films = films)
                    }

                //val films = repository.getFilmsByCollectionId(collectionId).first()

            } catch (_: IOException) {
                FilmsLocalUiState.Error
            } catch (_: HttpException) {
                FilmsLocalUiState.Error
            }
        }
    }

}