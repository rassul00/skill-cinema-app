package com.example.skillcinemaapp.presentation.list_film


import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.home.FilmCard
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute
import com.example.skillcinemaapp.presentation.ui.app.mainColor


@Composable
fun ListFilmPage(
    navController: NavController,
    listFilmPageViewModel: ListFilmPageViewModel = hiltViewModel()
) {

    val uiState by listFilmPageViewModel.listFilmUiState.collectAsState()

    when (uiState) {
        is ListFilmUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is ListFilmUiState.Success -> {
            val films = (uiState as ListFilmUiState.Success).films

            Scaffold(
                topBar = {
                    Header(listFilmPageViewModel.category,
                        onBackClick = {
                            listFilmPageViewModel.onEvent(ListFilmIntent.NavigateToBack(
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
                                listFilmPageViewModel.onEvent(
                                    ListFilmIntent.NavigateToFilmDetail(
                                        navigateToFilmDetail = {
                                            navController.navigate(
                                                FilmDetailRoute.FilmDetail.passId(
                                                    films[index].id.toString()
                                                )
                                            )
                                        }
                                    )
                                )
                            })
                        }
                    }
                }
            }
        }

        is ListFilmUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(text: String, onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back),
                    contentDescription = null,
                    tint = mainColor,
                    modifier = Modifier
                        .padding(start = 11.dp)
                )
            }

        },
        title = {
            Text(
                text = text,
                fontSize = 18.sp
            )

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )

    )
}