package com.example.skillcinemaapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.skillcinemaapp.presentation.common.SharedDataViewModel

@Composable
fun MainNavGraph(navController: NavHostController) {
    val sharedDataViewModel: SharedDataViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Graph.HOME_GRAPH,
        route = Graph.MAIN_GRAPH
    ) {
        HomeNavGraph(navController)
        SearchNavGraph(navController, sharedDataViewModel)
        ProfileNavGraph(navController)
    }
}
