package com.example.skillcinemaapp.presentation.period

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class PeriodPageViewModel @Inject constructor() : ViewModel() {


    private val _periodUiState = MutableStateFlow<PeriodUiState>(PeriodUiState.Success)
    val periodUiState: StateFlow<PeriodUiState> = _periodUiState

    fun onEvent(event: PeriodIntent) {
        when (event) {
            is PeriodIntent.NavigateToBack -> {
                event.navigateToBack()
            }
        }

    }
}