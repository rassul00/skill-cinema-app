package com.example.skillcinemaapp.presentation.country

import com.example.skillcinemaapp.data.model.FilterCountry

sealed interface CountryUiState {
    object Loading : CountryUiState
    data class Success(val countries : List<FilterCountry>) : CountryUiState
    object Error : CountryUiState
}