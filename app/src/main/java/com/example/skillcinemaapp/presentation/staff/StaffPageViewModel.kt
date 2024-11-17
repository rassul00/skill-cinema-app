package com.example.skillcinemaapp.presentation.staff

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
class StaffPageViewModel @Inject constructor(val repository : RepositoryImpl, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _staffUiState = MutableStateFlow<StaffUiState>(StaffUiState.Loading)
    val staffUiState: StateFlow<StaffUiState> = _staffUiState


    var staffId = savedStateHandle.get<String>("staffId") ?: ""


    init {
        onEvent(StaffIntent.Load)
    }
    fun onEvent(event: StaffIntent) {
        when (event) {
            is StaffIntent.Load ->
                get()
            is StaffIntent.NavigateToBack -> {
                event.navigateToBack()
            }
            is StaffIntent.NavigateToFilmDetail -> {
                event.navigateToFilmDetail()
            }
            is StaffIntent.NavigateToFilmography -> {
                event.navigateToFilmography()
            }
        }
    }

    private fun get() {
        viewModelScope.launch {
            _staffUiState.value = StaffUiState.Loading
            _staffUiState.value = try {

                val staff = repository.getStaffById(staffId)

                val maxFilms = 50
                for (index in 0 until minOf(staff.films.size, maxFilms)) {
                    val curr = staff.films[index]
                    val fullInfo = repository.getFilmById(curr.id.toString())
                    curr.poster = fullInfo.poster
                    curr.genres = fullInfo.genres
                    curr.rating = fullInfo.rating
                }

                StaffUiState.Success(
                    staff = staff
                )


            } catch (_: IOException) {
                StaffUiState.Error
            } catch (_: HttpException) {
                StaffUiState.Error
            }
        }
    }


}