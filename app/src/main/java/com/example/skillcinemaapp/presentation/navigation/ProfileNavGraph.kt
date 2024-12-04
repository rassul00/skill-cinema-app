package com.example.skillcinemaapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.skillcinemaapp.presentation.films_local.FilmsLocalPage
import com.example.skillcinemaapp.presentation.profile.ProfilePage

sealed class ProfileRoute(val route: String) {
    object Profile : ProfileRoute("profile")
    object Collection : ProfileRoute("collection/{collectionId}"){
        fun passCollection(collectionId: Int) : String{
            return "collection/$collectionId"
        }
    }
}


fun NavGraphBuilder.ProfileNavGraph(navController: NavHostController) {

    navigation(
        startDestination = ProfileRoute.Profile.route,
        route = Graph.PROFILE_GRAPH
    ) {
        composable(ProfileRoute.Profile.route) {
            ProfilePage(
                navController = navController
            )
        }

        composable(ProfileRoute.Collection.route,
            arguments = listOf(navArgument("collectionId") {
                type = NavType.IntType
            })
        ) {
            FilmsLocalPage(
                navController = navController
            )
        }

    }
}