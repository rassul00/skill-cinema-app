package com.example.skillcinemaapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.Film
import com.example.skillcinemaapp.ui.theme.genreTextColor
import com.example.skillcinemaapp.ui.theme.mainColor
import com.example.skillcinemaapp.ui.theme.textColor


//    val films = listOf(
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8),
//        Film("", "Близкие", listOf( Genre("драма") ), 7.8)
//    )





@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(uiState: UiState, onAllClick: (String, List<Film>) -> Unit, onFilmClick: () -> Unit){

    when (uiState) {
        is UiState.Initial -> {}
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            Scaffold(
                topBar = {
                    Header()
                }
            ){ paddingValues ->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(top = 30.dp, bottom = 30.dp, start = 26.dp, end = 26.dp),
                    contentPadding = paddingValues
                ) {

                    val categories = listOf("Топ-250", "Популярное", "Фильмы о комиксах", "Сериалы")

                    items(categories.size) { index ->
                        FilmsView(categories[index], uiState.films[index], { onAllClick(categories[index], uiState.films[index]) }, onFilmClick)
                    }

                }
            }

        }
        is UiState.Error -> ErrorScreen( modifier = Modifier.fillMaxSize())
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(){

    TopAppBar(
        title = {
            Text(
                text = "Skillcinema",
                color = textColor,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp, bottom = 15.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )

}

@Composable
fun FilmsView(categoryOfFilms: String, films: List<Film>, onAllClick: () -> Unit, onFilmClick: () -> Unit){

    CategoryHeader(categoryOfFilms, onAllClick)

    LazyRow(
        modifier = Modifier
            .padding(bottom = 36.dp)
    ){
        items(films.take(8).size) { index ->
            FilmCard(films[index], onFilmClick)
        }

        item{
            ShowAllButton(onAllClick)
        }
    }
}


@Composable
fun CategoryHeader(category: String, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = category,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            color = textColor
        )

        Text(
            text = "Все",
            fontSize = 14.sp,
            color = mainColor,
            modifier = Modifier
                .clickable(onClick = onClick)
        )
    }
}


@Composable
fun FilmCard(film: Film, onClick: () -> Unit){
    Column (
        modifier = Modifier
            .width(111.dp)
            .padding(end = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .width(111.dp)
                .height(156.dp)
        ){
            Image(
                painter = rememberAsyncImagePainter(model = film.poster),
                contentDescription = null,
            )

            LabelRating(film.rating)
        }

        Text(
            text = film.name,
            fontSize = 14.sp,
            fontWeight = W400,
            color = textColor,
            modifier = Modifier
//                .align(alignment = Alignment.BottomStart)
//                .padding(bottom = 16.dp)
        )

        Text(
            text = film.genres.firstOrNull()?.genre.orEmpty(),
            fontSize = 14.sp,
            fontWeight = W400,
            color = genreTextColor,
//            modifier = Modifier
//                .align(alignment = Alignment.BottomStart)
        )
    }
}



@Composable
fun LabelRating(rating: Double?){
   Row {
       Spacer(Modifier.weight(1f))
       Box(
           modifier = Modifier
               .padding(6.dp)
               .width(17.dp)
               .height(10.dp)
               .clip(RoundedCornerShape(4.dp))
               .background(mainColor)


       ){
           Text(
               text = rating.toString(),
               color = Color(255, 255, 255),
               fontSize = 6.sp,
               modifier = Modifier
                   .align(Alignment.Center)
           )
       }
   }
}


@Composable
fun ShowAllButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(111.dp)
            .height(156.dp)
            .padding(bottom = 15.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null
        )

        Text(
            text = "Показать все",
            fontSize = 12.sp,
            color = textColor,
            fontWeight = W400
        )
    }
}



@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }

}


@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.connection_error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp))
    }
}