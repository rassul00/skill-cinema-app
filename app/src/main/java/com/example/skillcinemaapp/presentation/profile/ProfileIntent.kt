package com.example.skillcinemaapp.presentation.profile


sealed interface ProfileIntent {
    object Load : ProfileIntent
    data class NavigateToCollection(val navigateToCollection: () -> Unit) : ProfileIntent
    data class AddNewCollection(val collectionName: String) : ProfileIntent
    data class ClearHistory(val collectionId: Int) : ProfileIntent
    data class RemoveCollectionWithFilms(val collectionId: Int) : ProfileIntent
}