package com.example.skillcinemaapp.page

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.skillcinemaapp.domain.bar.BottomBar
import com.example.skillcinemaapp.domain.graph.MainNavGraph


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun MainPage(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }

    ) {
        MainNavGraph(navController)
    }
}