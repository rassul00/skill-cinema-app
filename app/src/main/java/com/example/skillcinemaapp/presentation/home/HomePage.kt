package com.example.skillcinemaapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute
import com.example.skillcinemaapp.presentation.navigation.HomeRoute
import com.example.skillcinemaapp.presentation.ui.app.genreTextColor
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor



@Composable
fun HomePage(
    navController: NavController,
    homePageViewModel: HomePageViewModel = hiltViewModel()
) {
    val uiState by homePageViewModel.homeUiState.collectAsState()

    when (uiState) {
        is HomeUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is HomeUiState.Success -> {
            val films = (uiState as HomeUiState.Success).films


            Scaffold(topBar = { Header() }) { paddingValues ->

                val categories = listOf("Топ-250", "Популярное", "Фильмы о комиксах", "Сериалы")

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(top = 30.dp, bottom = 30.dp, start = 26.dp, end = 26.dp),
                    contentPadding = paddingValues
                ) {
                    items(categories.size) { index ->
                        CategoryWithFilmsRow(
                            navController = navController,
                            viewModel = homePageViewModel,
                            category = categories[index],
                            films = films.getOrNull(index).orEmpty()
                        )
                    }
                }
            }
        }
        is HomeUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}


@Composable
fun CategoryWithFilmsRow(
    navController: NavController,
    viewModel: HomePageViewModel,
    category: String,
    films: List<Film>
) {
    CategoryHeader(category) {
        viewModel.onEvent(
            HomeIntent.NavigateToListFilm {
                navController.navigate(HomeRoute.AllFilm.passCategory(category))
            }
        )
    }
    val filteredFilms = films.filter { it.name != null && it.rating != null && it.genres.isNotEmpty() }

    LazyRow(
        modifier = Modifier.padding(bottom = 36.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(minOf(filteredFilms.size, 8)) { index ->
            FilmCard(filteredFilms[index]) {
                viewModel.onEvent(
                    HomeIntent.NavigateToFilmDetail {
                        navController.navigate(FilmDetailRoute.FilmDetail.passId(filteredFilms[index].id.toString()))
                    }
                )
            }
        }
        item { ShowAllButton { viewModel.onEvent(HomeIntent.NavigateToListFilm { navController.navigate(HomeRoute.AllFilm.passCategory(category)) }) } }
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
            fontWeight = W500,
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
fun FilmCard(film: Film, onClick: () -> Unit) {
    Column(
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
        ) {
            Image(
                painter = rememberAsyncImagePainter(film.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            film.rating?.let { LabelRating(it) }
        }

        Text(film.name.orEmpty(), fontSize = 14.sp, fontWeight = W400, color = textColor)
        Text(film.genres[0].genre, fontSize = 14.sp, fontWeight = W400, color = genreTextColor)
    }
}

@Composable
fun LabelRating(rating: Double) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(mainColor)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(text = rating.toString(), color = Color.White, fontSize = 6.sp)
    }
}

@Composable
fun ShowAllButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(111.dp)
            .height(156.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(25.dp)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                tint = mainColor,
                contentDescription = null
            )
        }
        Text("Показать все", fontSize = 12.sp, color = textColor, fontWeight = W400)
    }
}
