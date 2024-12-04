package com.example.skillcinemaapp.presentation.common

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SharedDataViewModel @Inject constructor() : ViewModel() {
    private val _type = MutableStateFlow<String>("Все")
    val type: StateFlow<String> = _type

    private val _country = MutableStateFlow<String>("Россия")
    val country: StateFlow<String> = _country

    private val _countryId = MutableStateFlow<String>("34")
    val countryId: StateFlow<String> = _countryId

    private val _genre = MutableStateFlow<String>("комедия")
    val genre: StateFlow<String> = _genre

    private val _genreId = MutableStateFlow<String>("13")
    val genreId: StateFlow<String> = _genreId

    private val _fromPer = MutableStateFlow<Int>(1998)
    val fromPer: StateFlow<Int> = _fromPer

    private val _toPer = MutableStateFlow<Int>(2017)
    val toPer: StateFlow<Int> = _toPer

    private val _ratingFrom = MutableStateFlow<Float>(6f)
    val ratingFrom: StateFlow<Float> = _ratingFrom

    private val _ratingTo = MutableStateFlow<Float>(9f)
    val ratingTo: StateFlow<Float> = _ratingTo

    private val _order = MutableStateFlow<String>("Дата")
    val order: StateFlow<String> = _order


    fun updateType(data: String) {
        _type.value = data
    }

    fun updateCountry(data: String) {
        _country.value = data
    }

    fun updateCountryId(data: String){
        _countryId.value = data
    }

    fun updateGenre(data: String){
        _genre.value = data
    }

    fun updateGenreId(data: String){
        _genreId.value = data
    }

    fun updateFromPer(data: Int){
        _fromPer.value = data
    }

    fun updateToPer(data: Int){
        _toPer.value = data
    }

    fun updateRatingFrom(data: Float){
        _ratingFrom.value = data
    }

    fun updateRatingTo(data: Float){
        _ratingTo.value = data
    }

    fun updateOrder(data: String){
        _order.value = data
    }
}