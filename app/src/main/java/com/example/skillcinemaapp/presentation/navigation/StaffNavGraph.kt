package com.example.skillcinemaapp.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.skillcinemaapp.presentation.filmography.FilmographyPage
import com.example.skillcinemaapp.presentation.staff.StaffPage


const val staffId = "staffId"

sealed class StaffRoute(val route: String) {
    object Staff : StaffRoute("staff/{$staffId}"){
        fun passId(staffId: String): String {
            return "staff/$staffId"
        }
    }
    object Filmography : StaffRoute("filmography/{$staffId}"){
        fun passId(staffId: String): String {
            return "filmography/$staffId"
        }
    }
}

fun NavGraphBuilder.StaffNavGraph(navController: NavController) {

    navigation(
        startDestination = StaffRoute.Staff.route,
        route = Graph.STAFF_GRAPH
    ){
        composable(
            StaffRoute.Staff.route,
            arguments = listOf(navArgument(staffId) {
                type = NavType.StringType
            })
        ){
            StaffPage(navController)
        }

        composable(
            StaffRoute.Filmography.route,
            arguments = listOf(navArgument(staffId) {
                type = NavType.StringType
            })
        ){
            FilmographyPage(navController)
        }
    }
}