package com.example.skillcinemaapp.presentation.staff

import com.example.skillcinemaapp.data.model.Staff

sealed interface StaffUiState {
    object Loading : StaffUiState
    data class Success(val staff: Staff) : StaffUiState
    object Error : StaffUiState
}