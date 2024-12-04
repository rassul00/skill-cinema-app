package com.example.skillcinemaapp.presentation.film_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import com.example.skillcinemaapp.data.room.Collection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmDetailPageViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _filmDetailUiState = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val filmDetailUiState: MutableStateFlow<FilmDetailUiState> = _filmDetailUiState


    var favoriteCollectionId: Int = 0
    var watchLaterCollectionId: Int = 0

    var id = savedStateHandle.get<String>("id") ?: ""


    private val _isFilmInCollection = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val isFilmInCollection: StateFlow<Map<Int, Boolean>> = _isFilmInCollection


    init {
        onEvent(FilmDetailIntent.Load)
        viewModelScope.launch {
            favoriteCollectionId = repository.getCollectionIdByName("Любимое").toInt()
            watchLaterCollectionId = repository.getCollectionIdByName("Хочу посмотреть").toInt()
        }
    }

    fun onEvent(event: FilmDetailIntent){
        when (event) {
            is FilmDetailIntent.Load ->
                get()
            is FilmDetailIntent.NavigateToGallery -> {
                event.navigateToGallery()
            }
            is FilmDetailIntent.NavigateToBack -> {
                event.navigateToBack()
            }
            is FilmDetailIntent.NavigateToStaff -> {
                event.navigateToStaff()
            }
            is FilmDetailIntent.SaveFilmToCollection -> {
                viewModelScope.launch{

                    repository.addFilm(collectionId = event.collectionId, film = event.film)
                    repository.increaseCollectionSize(event.collectionId)


                    repository.getCollections()
                        .collect { collections ->
                            isFilmInCollection(collections)
                            (filmDetailUiState.value as? FilmDetailUiState.Success)?.let {
                                _filmDetailUiState.value = (it).copy(
                                    collections = collections
                                )
                            }
                        }
                }
            }
            is FilmDetailIntent.AddNewCollection -> {
                viewModelScope.launch {
                    repository.addCollection(Collection(name = event.collectionName))
                    repository.getCollections()
                        .collect { collections ->

                            (filmDetailUiState.value as? FilmDetailUiState.Success)?.let {
                                _filmDetailUiState.value = (it).copy(
                                    collections = collections
                                )
                            }
                        }
                }
            }
            is FilmDetailIntent.DeleteFilmFromCollection -> {
                viewModelScope.launch{
                    repository.decreaseCollectionSize(event.collectionId)
                    repository.deleteFilmFromCollection(filmId = event.filmId, collectionId = event.collectionId)
                    repository.getCollections()
                        .collect { collections ->
                            isFilmInCollection(collections)
                            (filmDetailUiState.value as? FilmDetailUiState.Success)?.let {
                                _filmDetailUiState.value = (it).copy(
                                    collections = collections
                                )
                            }
                        }



                }
            }
        }
    }

    private fun get(){
        viewModelScope.launch{
            _filmDetailUiState.value = FilmDetailUiState.Loading
            try {
                val film = repository.getFilmById(id)
                val staff = repository.getStaffOfFilm(id)
                val images = repository.getImagesOfFilm(id)
                val similarFilms = repository.getSimilarFilms(id)


                similarFilms.forEach{ curr ->
                    val fullInfo = repository.getFilmById(curr.id.toString())
                    curr.poster = fullInfo.poster
                    curr.genres = fullInfo.genres
                    curr.rating = fullInfo.rating
                }




                repository.getCollections()
                    .collect { collections ->
                        isFilmInCollection(collections)

                        _filmDetailUiState.value = FilmDetailUiState.Success(film = film, filmDetailStaffs = staff, images = images, filmDetailSimilarFilms = similarFilms, collections = collections)
                    }


            }
            catch (_: IOException) {
                FilmDetailUiState.Error
            }
            catch (_: HttpException) {
                FilmDetailUiState.Error
            }
        }

    }

    private fun isFilmInCollection(collections: List<Collection>) {
        viewModelScope.launch{
            val result = mutableMapOf<Int, Boolean>()
            collections.forEach{ collection ->
                result[collection.id] = repository.isFilmInCollection(filmId = id.toInt(), collectionId = collection.id)
            }
            _isFilmInCollection.value = result

        }
    }
}