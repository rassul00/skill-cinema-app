package com.example.skillcinemaapp.domain.graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skillcinemaapp.presentation.HomePageViewModel
import com.example.skillcinemaapp.presentation.ScreenContent
import com.example.skillcinemaapp.presentation.HomePage
import com.example.skillcinemaapp.presentation.ListFilmPage



sealed class HomeRoute(val route: String) {
    object Home : HomeRoute("home")
    object AllFilm : HomeRoute("list_film/{category}"){
        fun createRoute(category: String): String {
            return "list_film/$category"
        }
    }
    object FilmDetail : HomeRoute("film_detail")
}

@Composable
fun HomeNavGraph() {
    val navController = rememberNavController()
    val homePageViewModel: HomePageViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = HomeRoute.Home.route,
        route = Graph.HOME_GRAPH
    ) {

        composable(HomeRoute.Home.route) {

            HomePage(
                uiState = homePageViewModel.uiState,
                onAllClick = { category, films ->
                    homePageViewModel.setFilms(films)

                    navController.navigate(HomeRoute.AllFilm.createRoute(category))
                },
                onFilmClick = {
                    navController.navigate(HomeRoute.FilmDetail.route)
                }
            )
        }

        composable(
            route = HomeRoute.AllFilm.route,
            arguments = listOf(navArgument("category"){type = NavType.StringType})
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Заглушка"
            val films = homePageViewModel.getFilm()
            ListFilmPage(
                category = category,
                films = films,
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