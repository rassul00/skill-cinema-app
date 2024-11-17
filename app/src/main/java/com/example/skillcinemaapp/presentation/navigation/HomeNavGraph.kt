package com.example.skillcinemaapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

import com.example.skillcinemaapp.presentation.home.HomePage
import com.example.skillcinemaapp.presentation.list_film.ListFilmPage


const val category = "category"
const val idOfFilm = "id"

sealed class HomeRoute(val route: String) {
    object Home : HomeRoute("home")
    object AllFilm : HomeRoute("list_film/{$category}"){
        fun passCategory(category: String) : String{
            return "list_film/$category"
        }
    }
    object FilmDetail : HomeRoute("film_detail/{$idOfFilm}") {
        fun passId(idOfFilm: String): String {
            return "film_detail/$idOfFilm"
        }
    }
}



fun NavGraphBuilder.HomeNavGraph(navController: NavHostController) {

    navigation(
        startDestination = HomeRoute.Home.route,
        route = Graph.HOME_GRAPH
    ) {
        composable(HomeRoute.Home.route) {



            HomePage(
                navController = navController
            )
        }


        composable(
            HomeRoute.AllFilm.route,
            arguments = listOf(navArgument(category) {
                type = NavType.StringType
            })

        ) {

            ListFilmPage(
                navController = navController
            )

        }

        FilmDetailNavGraph(navController)
    }
}

