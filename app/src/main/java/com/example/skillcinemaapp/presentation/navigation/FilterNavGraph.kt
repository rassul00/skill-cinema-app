package com.example.skillcinemaapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.skillcinemaapp.presentation.common.SharedDataViewModel
import com.example.skillcinemaapp.presentation.country.CountryPage
import com.example.skillcinemaapp.presentation.films_by_filter.FilmsByFilterPage
import com.example.skillcinemaapp.presentation.filter.FilterPage
import com.example.skillcinemaapp.presentation.genre.GenrePage
import com.example.skillcinemaapp.presentation.period.PeriodPage



sealed class FilterRoute(val route: String) {
    object Filter : FilterRoute("filter")
    object Country : FilterRoute("country")
    object Genre : FilterRoute("genre")
    object Period : FilterRoute("period")

    object FilmsByFilter : FilterRoute("filmsByFilter/{country}/{countryId}/{genre}/{genreId}/{order}/{type}/{ratingFrom}/{ratingTo}/{fromPer}/{toPer}") {
        fun passFilter(
            country: String,
            countryId: String,
            genre: String,
            genreId: String,
            order: String,
            type: String,
            ratingFrom: String,
            ratingTo: String,
            fromPer: String,
            toPer: String
        ): String {
            return "filmsByFilter/$country/$countryId/$genre/$genreId/$order/$type/$ratingFrom/$ratingTo/$fromPer/$toPer"
        }
    }
}

fun NavGraphBuilder.FilterNavGraph(navController: NavHostController, sharedDataViewModel: SharedDataViewModel) {


    navigation(
        startDestination = FilterRoute.Filter.route,
        route = Graph.FILTER_GRAPH
    ) {
        composable(FilterRoute.Filter.route
        ) {
            FilterPage(
                navController = navController,
                sharedDataViewModel = sharedDataViewModel
            )
        }

        composable(FilterRoute.Country.route) {

            CountryPage(
                navController = navController,
                sharedDataViewModel = sharedDataViewModel
            )
        }
        composable(FilterRoute.Genre.route) {
            GenrePage(
                navController = navController,
                sharedDataViewModel = sharedDataViewModel
            )
        }
        composable(FilterRoute.Period.route) {
            PeriodPage(
                navController = navController,
                sharedDataViewModel = sharedDataViewModel
            )
        }

        composable(FilterRoute.FilmsByFilter.route,
            arguments = listOf(
                navArgument("country") { type = NavType.StringType },
                navArgument("countryId") { type = NavType.StringType },
                navArgument("genre") { type = NavType.StringType },
                navArgument("genreId") { type = NavType.StringType },
                navArgument("order") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("ratingFrom") { type = NavType.StringType },
                navArgument("ratingTo") { type = NavType.StringType },
                navArgument("fromPer") { type = NavType.StringType },
                navArgument("toPer") { type = NavType.StringType }
            )
        ){
            FilmsByFilterPage(
                navController = navController
            )
        }
    }
}