package com.example.skillcinemaapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.Film

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListFilmPage(category: String, films: List<Film>, onBackClick: () -> Unit, onFilmClick: () -> Unit){

    Scaffold(
        topBar = { Header(category, onBackClick) }
    ){ paddingValues ->

        LazyVerticalGrid(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 60.dp, start = 26.dp, end = 26.dp),
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(films.take(40).size) { index ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    FilmCard(films[index], onFilmClick)
                }

            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(category: String, onClick: () -> Unit){
    CenterAlignedTopAppBar(
        navigationIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(start = 10.dp)
            )
        },
        title = {
            Text(
                text = category,
                fontSize = 18.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}




