package com.example.skillcinemaapp.graph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skillcinemaapp.page.MainPage
import com.example.skillcinemaapp.page.OnBoardingPage



@Composable
fun RootNavGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Graph.ONBOARDING_PAGE,
        route = Graph.ROOT,
    ){

        composable(Graph.ONBOARDING_PAGE){
            OnBoardingPage(onClick = {
                navController.navigate(Graph.MAIN_SCREEN_PAGE){
                    popUpTo(Graph.ONBOARDING_PAGE) {
                        inclusive = true
                    }
                }
            })
        }

        composable(Graph.MAIN_SCREEN_PAGE){
            MainPage()
        }
    }
}
