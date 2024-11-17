package com.example.skillcinemaapp.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skillcinemaapp.presentation.bar.BottomBar
import com.example.skillcinemaapp.presentation.navigation.MainNavGraph


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavHostController = rememberNavController()){

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }

    ) {
        MainNavGraph(navController)
    }
}