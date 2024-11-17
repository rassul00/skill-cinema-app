package com.example.skillcinemaapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skillcinemaapp.presentation.MainPage
import com.example.skillcinemaapp.presentation.onboarding.OnBoardingPage




@Composable
fun RootNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Graph.MAIN_GRAPH,
        route = Graph.ROOT,
    ){

        composable(Graph.ONBOARDING_PAGE){
            OnBoardingPage(navController)
        }


        composable(Graph.MAIN_GRAPH){
            MainPage()
        }
    }
}
