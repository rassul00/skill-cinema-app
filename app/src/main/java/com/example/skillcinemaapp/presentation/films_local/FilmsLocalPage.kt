package com.example.skillcinemaapp.presentation.films_local

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.list_film.Header
import com.example.skillcinemaapp.presentation.navigation.ProfileRoute
import com.example.skillcinemaapp.presentation.profile.FilmCard
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor


@Composable
fun FilmsLocalPage(
    navController: NavController,
    filmsLocalPageViewModel: FilmsLocalPageViewModel = hiltViewModel()
){

    val uiState by filmsLocalPageViewModel.filmsLocalUiState.collectAsState()

    when(uiState) {
        is FilmsLocalUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is FilmsLocalUiState.Success -> {


            val collectionId = (uiState as FilmsLocalUiState.Success).collectionId
            val collectionName = (uiState as FilmsLocalUiState.Success).collectionName
            val films = (uiState as FilmsLocalUiState.Success).films

            Scaffold(
                topBar = {
                    Header(
                        collectionName,
                        onBackClick = {
                            filmsLocalPageViewModel.onEvent(FilmsLocalIntent.NavigateToBack(
                                navigateToBack = {
                                    navController.navigate(ProfileRoute.Profile.route)
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
                            FilmCard(films[index])
                        }
                    }


                    item(span = { GridItemSpan(2) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            IconButton(
                                onClick = {
                                    filmsLocalPageViewModel.onEvent(FilmsLocalIntent.ClearHistory(
                                        collectionId = collectionId
                                    ))
                                },
                                modifier = Modifier.size(25.dp)
                            ){
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.draft),
                                    tint = mainColor,
                                    contentDescription = null
                                )
                            }
                            Text("Очистить\nисторию", fontSize = 12.sp, color = textColor, fontWeight = W400)
                        }
                    }
                }
            }
        }
        is FilmsLocalUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}


