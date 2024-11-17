package com.example.skillcinemaapp.presentation.film_detail

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
class FilmDetailViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _filmDetailUiState = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val filmDetailUiState: MutableStateFlow<FilmDetailUiState> = _filmDetailUiState



    var id = savedStateHandle.get<String>("id") ?: ""


    init {
        onEvent(FilmDetailIntent.Load)
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
        }
    }

    private fun get(){
        viewModelScope.launch{
            _filmDetailUiState.value = FilmDetailUiState.Loading
            _filmDetailUiState.value = try {
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

                FilmDetailUiState.Success(film = film, filmDetailStaffs = staff, images = images, filmDetailSimilarFilms = similarFilms)

            }
            catch (_: IOException) {
                FilmDetailUiState.Error
            }
            catch (_: HttpException) {
                FilmDetailUiState.Error
            }
        }

    }



//    fun onBackClick(navController: NavController){
//        navController.popBackStack()
//        val origin = navController.previousBackStackEntry?.arguments?.getString("origin")
//        if(origin == HomeRoute.Home.route){
//            navController.navigate(HomeRoute.Home.route)
//        }
//        else if (origin == HomeRoute.AllFilm.route){
//            navController.navigate(HomeRoute.AllFilm.route)
//        }
//    }


}