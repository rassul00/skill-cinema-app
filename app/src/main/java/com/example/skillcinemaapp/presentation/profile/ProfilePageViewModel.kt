package com.example.skillcinemaapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import com.example.skillcinemaapp.data.room.Collection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ProfilePageViewModel @Inject constructor(val repository : RepositoryImpl) : ViewModel() {


    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Initial)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState




    fun onEvent(event: ProfileIntent) {
        when (event) {
            is ProfileIntent.Load -> {
                get()
            }

            is ProfileIntent.NavigateToCollection -> {
                event.navigateToCollection()
            }

            is ProfileIntent.AddNewCollection -> {
                viewModelScope.launch {
                    repository.addCollection(Collection(name = event.collectionName))

                    repository.getCollections()
                        .collect { collections ->
                            (_profileUiState.value as? ProfileUiState.Success)?.let {
                                _profileUiState.value = (it).copy(
                                    collections = collections
                                )
                            }
                        }

                }
            }

            is ProfileIntent.ClearHistory -> {
                viewModelScope.launch {
                    repository.clearCollection(event.collectionId)
                    repository.deleteFilmsFromCollection(event.collectionId)
                    repository.getCollections()
                        .collect { collections ->
                            (_profileUiState.value as? ProfileUiState.Success)?.let {
                                _profileUiState.value = (it).copy(
                                    collections = collections,
                                    films = emptyList()
                                )
                            }

                        }
//                    withContext(Dispatchers.Main)


                }
            }

            is ProfileIntent.RemoveCollectionWithFilms -> {
                viewModelScope.launch{
                    repository.removeCollectionAndFilms(event.collectionId)
                    repository.getCollections()

                        .collect { collections ->
                            (_profileUiState.value as? ProfileUiState.Success)?.let {
                                _profileUiState.value = (it).copy(
                                    collections = collections
                                )
                            }

                        }


                }
            }

        }
    }

    var viewedCollectionId = 0


    private fun get() {

        viewModelScope.launch {

            try {

                repository.getCollections()
                    .collect { collections ->
                        val viewedCollection = collections.find { it.name == "Просмотрено" }

                        if (viewedCollection != null) {
                            viewedCollectionId = viewedCollection.id
                            repository.getFilmsByCollectionId(collectionId = viewedCollection.id)
                                .collect { viewedFilms ->
                                    _profileUiState.value = ProfileUiState.Success(
                                        collections = collections,
                                        films = viewedFilms
                                    )
                                }
                        }
                        else {
                            _profileUiState.value = ProfileUiState.Success(
                                collections = collections,
                                films = emptyList()
                            )
                        }
                    }

            }
            catch (_: IOException) {
                _profileUiState.value = ProfileUiState.Error
            }
        }
    }
}