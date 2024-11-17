package com.example.skillcinemaapp.presentation.gallery



sealed interface GalleryIntent {
    object Load : GalleryIntent
    data class NavigateToBack(val navigateToBack: () -> Unit) : GalleryIntent
}