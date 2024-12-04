package com.example.skillcinemaapp.presentation.filter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.skillcinemaapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FilterPageViewModel @Inject constructor(val repository : RepositoryImpl, val savedStateHandle: SavedStateHandle) : ViewModel() {

    private var _filterUiState = MutableStateFlow<FilterUiState>(FilterUiState.Success)

    val filterUiState: StateFlow<FilterUiState> = _filterUiState



    fun onEvent(event: FilterIntent) {
        when (event) {
            is FilterIntent.NavigateToBack -> {
                event.navigateToBack()
            }
            is FilterIntent.NavigateToCountry -> {

                event.navigateToCountry()

            }
            is FilterIntent.NavigateToGenre -> {

                event.navigateToGenre()

            }
            is FilterIntent.NavigateToPeriod -> {

                event.navigateToPeriod()
            }

            is FilterIntent.NavigateToFilmsByFilter -> {
                event.navigateToFilmsByFilter()
            }

        }
    }
}