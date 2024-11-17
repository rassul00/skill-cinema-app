package com.example.skillcinemaapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

import com.example.skillcinemaapp.presentation.film_detail.FilmDetailPage
import com.example.skillcinemaapp.presentation.gallery.GalleryPage




sealed class FilmDetailRoute(val route: String) {

    object FilmDetail : FilmDetailRoute("film_detail/{$idOfFilm}"){
        fun passId(idOfFilm: String): String {
            return "film_detail/$idOfFilm"
        }
    }
    object Gallery : FilmDetailRoute("gallery/{$idOfFilm}"){
        fun passId(idOfFilm: String): String {
            return "gallery/$idOfFilm"
        }
    }
}



fun NavGraphBuilder.FilmDetailNavGraph(navController: NavHostController) {

    navigation(
        startDestination = FilmDetailRoute.FilmDetail.route,
        route = Graph.FILM_DETAIL_GRAPH
    ) {
        composable(
            FilmDetailRoute.FilmDetail.route,
            arguments = listOf(navArgument(idOfFilm) {
                type = NavType.StringType
            })
        ) {
            FilmDetailPage(navController)
        }

        composable(
            FilmDetailRoute.Gallery.route,
            arguments = listOf(navArgument(idOfFilm) {
                type = NavType.StringType
            })
        ){
            GalleryPage(navController)
        }

        StaffNavGraph(navController)

    }

}

