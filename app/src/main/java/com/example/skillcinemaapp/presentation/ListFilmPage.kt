package com.example.skillcinemaapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.Film
import com.example.skillcinemaapp.page.LabelRating
import com.example.skillcinemaapp.ui.theme.genreTextColor
import com.example.skillcinemaapp.ui.theme.textColor

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
    ){ paddingValues ->

        LazyVerticalGrid(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 36.dp),
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(films.size) { index ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    FilmCard(films[index], onFilmClick)
                }
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
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = onClick)
                        .align(Alignment.TopStart)
                )
                Text(
                    text = category,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}


@Composable
fun FilmCard(film: Film, onClick: () -> Unit){
    Box (
        modifier = Modifier
            .width(111.dp)
            .height(194.dp)
            .padding(end = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .width(111.dp)
                .height(156.dp)
                .alpha(0.4f)
                .background(Color(181, 181, 204))
        ){
//            Image(
//                painter = painterResource(posterOfFilm),
//                contentDescription = null
//            )
        }

        LabelRating(film.rating)

        Text(
            text = film.name,
            fontSize = 14.sp,
            fontWeight = W400,
            color = textColor,
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .padding(bottom = 16.dp)
        )

        Text(
            text = film.genre,
            fontSize = 14.sp,
            fontWeight = W400,
            color = genreTextColor,
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
        )
    }
}



