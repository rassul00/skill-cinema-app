package com.example.skillcinemaapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.Film
import com.example.skillcinemaapp.page.FilmCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListFilmPage(category: String, onBackClick: () -> Unit, onFilmClick: () -> Unit){

    val films = listOf(
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8),
        Film("", "Близкие", "драма", 7.8)

    )
    Scaffold(
        topBar = { Header(category, onBackClick) }
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(films.size) { index ->
                FilmCard(films[index], onFilmClick)
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(category: String, onClick: () -> Unit){
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = onClick)
                        .align(Alignment.TopStart)
                )
                Text(
                    text = category,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
        }
    )


}



