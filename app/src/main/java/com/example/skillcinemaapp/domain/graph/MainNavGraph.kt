package com.example.skillcinemaapp.domain.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skillcinemaapp.presentation.ScreenContent

sealed class PageRoute(val route: String) {
    object Home : PageRoute("home")
    object Search : PageRoute("search")
    object Profile : PageRoute("profile")
}

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PageRoute.Home.route,
        route = Graph.MAIN_GRAPH
    ){

        composable(PageRoute.Home.route) {
            HomeNavGraph()
        }

        composable(PageRoute.Search.route) { ScreenContent(name = PageRoute.Search.route) }
        composable(PageRoute.Profile.route) { ScreenContent(name = PageRoute.Profile.route) }
    }
}
