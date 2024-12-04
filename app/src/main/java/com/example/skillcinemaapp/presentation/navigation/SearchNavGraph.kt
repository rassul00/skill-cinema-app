package com.example.skillcinemaapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.skillcinemaapp.presentation.common.SharedDataViewModel
import com.example.skillcinemaapp.presentation.search.SearchPage


sealed class SearchRoute(val route: String) {
    object Search : SearchRoute("search")
}

fun NavGraphBuilder.SearchNavGraph(navController: NavHostController, sharedDataViewModel: SharedDataViewModel) {

    navigation(
        startDestination = SearchRoute.Search.route,
        route = Graph.SEARCH_GRAPH
    ) {
        composable(SearchRoute.Search.route) {
            SearchPage(
                navController = navController
            )
        }

        FilterNavGraph(navController, sharedDataViewModel)

        FilmDetailNavGraph(navController)

    }
}