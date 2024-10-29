package com.example.skillcinemaapp.domain.graph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skillcinemaapp.ScreenContent
import com.example.skillcinemaapp.page.HomePage
import com.example.skillcinemaapp.presentation.ListFilmPage

sealed class HomeRoute(val route: String) {
    object Home : HomeRoute("home")
    object AllFilm : HomeRoute("list_film")
    object FilmDetail : HomeRoute("film_detail")
}

@Composable
fun HomeNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute.Home.route,
        route = Graph.HOME_GRAPH
    ) {

        composable(HomeRoute.Home.route) {
            HomePage(
                onAllClick = {
                    navController.navigate(HomeRoute.AllFilm.route)
                },
                onFilmClick = {
                    navController.navigate(HomeRoute.FilmDetail.route)
                }
            )
        }

        composable(HomeRoute.AllFilm.route) {
            ListFilmPage("Заглушка",
                onBackClick = {
                    navController.navigate(HomeRoute.Home.route)
                },
                onFilmClick = {
                    navController.navigate(HomeRoute.FilmDetail.route)
                }
            )
        }

        composable(HomeRoute.FilmDetail.route) {
            ScreenContent(name = HomeRoute.FilmDetail.route)
        }
    }
}