package com.example.skillcinemaapp.presentation.films_by_filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.home.FilmCard
import com.example.skillcinemaapp.presentation.list_film.Header
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute


@Composable
fun FilmsByFilterPage(navController: NavController, filmsByFilterPageViewModel: FilmsByFilterPageViewModel = hiltViewModel()) {

    val uiState by filmsByFilterPageViewModel.filmsByFilterUiState.collectAsState()

    when(uiState) {
        is FilmsByFilterUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is FilmsByFilterUiState.Success -> {
            val films = (uiState as FilmsByFilterUiState.Success).films

            Scaffold(
                topBar = {
                    Header("По фильтру",
                        onBackClick = {
                            filmsByFilterPageViewModel.onEvent(FilmsByFilterIntent.NavigateToBack(
                                navigateToBack = {
                                    navController.popBackStack()
                                }
                            ))
                        }
                    )
                }
            ) { paddingValues ->



                LazyVerticalGrid(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(top = 16.dp, bottom = 60.dp, start = 26.dp, end = 26.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(minOf(films.size, 40)) { index ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            FilmCard(films[index], onClick = {
                                filmsByFilterPageViewModel.onEvent(
                                    FilmsByFilterIntent.NavigateToFilmDetail(
                                        navigateToFilmDetail = {
                                            navController.navigate(FilmDetailRoute.FilmDetail.passId(films[index].id.toString()))
                                        }
                                    )
                                )
                            })
                        }
                    }
                }
            }
        }
        is FilmsByFilterUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}