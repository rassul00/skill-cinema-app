package com.example.skillcinemaapp.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skillcinemaapp.ScreenContent
import com.example.skillcinemaapp.page.HomePage
import com.example.skillcinemaapp.page.ProfilePage
import com.example.skillcinemaapp.page.SearchPage

sealed class PageRoute(val route: String) {
    object Home : PageRoute("Home")
    object Search : PageRoute("Search")
    object Profile : PageRoute("Profile")
    object AllFilm : PageRoute(route = "All Film")
    object FilmDetail : PageRoute(route = "Film Detail")
}

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PageRoute.Home.route
    ){

        composable(PageRoute.Home.route) {
            HomePage(
                onAllClick = {
                    navController.navigate(PageRoute.AllFilm.route)
                },
                onFilmClick = {
                    navController.navigate(PageRoute.FilmDetail.route)
                }
            )
        }
        composable(PageRoute.Search.route) { SearchPage(name = PageRoute.Search.route) }
        composable(PageRoute.Profile.route) { ProfilePage(name = PageRoute.Profile.route) }
        composable(PageRoute.AllFilm.route){ ScreenContent(name = PageRoute.AllFilm.route) }
        composable(PageRoute.FilmDetail.route){ ScreenContent(name = PageRoute.FilmDetail.route) }
    }
}
