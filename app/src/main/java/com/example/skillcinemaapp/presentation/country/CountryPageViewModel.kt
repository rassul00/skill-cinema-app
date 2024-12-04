package com.example.skillcinemaapp.presentation.country

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
class CountryPageViewModel @Inject constructor(val repository : RepositoryImpl) : ViewModel() {


    private val _countryUiState = MutableStateFlow<CountryUiState>(CountryUiState.Loading)
    val countryUiState: StateFlow<CountryUiState> = _countryUiState


    init {
        onEvent(
            CountryIntent.Load
        )
    }


    fun onEvent(event: CountryIntent) {
        when (event) {
            is CountryIntent.Load -> {
                get()
            }
            is CountryIntent.NavigateToBack -> {
                event.navigateToBack()
            }
        }

    }

    private fun get() {

        viewModelScope.launch {
            _countryUiState.value = CountryUiState.Loading
            _countryUiState.value = try {

                val countries = repository.getCountries()

                CountryUiState.Success(countries = countries)

            } catch (_: IOException) {
                CountryUiState.Error
            } catch (_: HttpException) {
                CountryUiState.Error
            }
        }
    }

}