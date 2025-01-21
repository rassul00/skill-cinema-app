package com.example.skillcinemaapp.presentation.search

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.model.SearchFilm
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute
import com.example.skillcinemaapp.presentation.navigation.FilterRoute
import com.example.skillcinemaapp.presentation.ui.app.genreTextColor
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPage(
    navController: NavController,
    searchPageViewModel: SearchPageViewModel = hiltViewModel()
) {

    val uiState by searchPageViewModel.searchUiState.collectAsState()

    var searchQuery by remember { mutableStateOf(searchPageViewModel.searchQuery.value) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 40.dp, bottom = 10.dp, start = 26.dp, end = 26.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE5E5E5), shape = RoundedCornerShape(24.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
                        tint = Color.Gray,
                        contentDescription = null
                    )
                    TextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            searchPageViewModel.onSearchQueryChanged(it)
                        },
                        textStyle = TextStyle(fontSize = 14.sp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        maxLines = 1,
                        placeholder = {
                            Text(
                                text = "Фильмы, актёры, режиссёры",
                                color = Color.Gray,
                                style = TextStyle(fontSize = 14.sp)
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.rect),
                        tint = Color.Gray,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        searchPageViewModel.onEvent(
                            SearchIntent.NavigateToFilter(
                                navigateToFilter = {
                                    navController.navigate(FilterRoute.Filter.route)
                                }
                            )
                        )
                    }, Modifier.size(25.dp)) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.filter),
                            tint = Color.Gray,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
    { paddingValues ->



        when (uiState) {
            is SearchUiState.Initial -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 26.dp, end = 26.dp)
                )
            }

            is SearchUiState.Success -> {
                val films = (uiState as SearchUiState.Success).films

                if (films.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(start = 26.dp, end = 26.dp)
                    ){
                        Text(
                            text = "К сожалению, по вашему запросу ничего не найдено",
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(top = 10.dp, bottom = 80.dp, start = 26.dp, end = 26.dp),

                        contentPadding = paddingValues,
                        verticalArrangement = Arrangement.spacedBy(20.dp)

                    ) {
                        items(minOf(films.size, 30)) { index ->
                            FilmCard(films[index], onClick = {
                                searchPageViewModel.onEvent(SearchIntent.NavigateToFilmDetail(
                                    navigateToFilmDetail = {
                                        navController.navigate(FilmDetailRoute.FilmDetail.passId(films[index].id.toString()))
                                    }
                                ))
                            })
                        }
                    }
                }
            }

            is SearchUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 26.dp, end = 26.dp)
                ){
                    Text(
                        text = "Ошибка загрузки. Попробуйте снова.",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun FilmCard(film: SearchFilm, onClick: () -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
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
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            film.rating?.let { LabelRating(it) }
        }

        Column {
            Text(
                text = film.name.orEmpty(),
                fontSize = 14.sp,
                fontWeight = W400,
                color = textColor,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = film.genres.firstOrNull()?.genre.orEmpty(),
                fontSize = 14.sp,
                fontWeight = W400,
                color = genreTextColor
            )
        }
    }
}

@Composable
fun LabelRating(rating: String) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(mainColor)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(text = rating, color = Color.White, fontSize = 6.sp)
    }
}



