package com.example.skillcinemaapp.presentation.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GalleryPageViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _galleryUiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Loading)
    val galleryUiState: MutableStateFlow<GalleryUiState> = _galleryUiState


    var id = savedStateHandle.get<String>("id") ?: ""


    init {
        onEvent(GalleryIntent.Load)
    }


    fun onEvent(event: GalleryIntent){
        when (event) {
            is GalleryIntent.Load ->
                get()
            is GalleryIntent.NavigateToBack -> {
                event.navigateToBack()
            }
        }
    }

    private fun get(){
        viewModelScope.launch{
            _galleryUiState.value = GalleryUiState.Loading
            _galleryUiState.value = try {
                val images = repository.getImagesOfFilm(id)

                GalleryUiState.Success(images = images)

            }
            catch (_: IOException) {
                GalleryUiState.Error
            }
            catch (_: HttpException) {
                GalleryUiState.Error
            }
        }

    }

}