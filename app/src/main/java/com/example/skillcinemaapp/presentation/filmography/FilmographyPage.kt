package com.example.skillcinemaapp.presentation.filmography

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.model.StaffFilm
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.home.LabelRating
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute
import com.example.skillcinemaapp.presentation.ui.app.genreTextColor
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor

@SuppressLint("AutoboxingStateCreation")
@Composable

fun FilmographyPage(navController: NavController, filmographyPageViewModel: FilmographyPageViewModel = hiltViewModel()){
    val uiState by filmographyPageViewModel.filmographyUiState.collectAsState()


    when(uiState){
        is FilmographyUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is FilmographyUiState.Success -> {
            val staff = (uiState as FilmographyUiState.Success).staff

            val filteredFilms = staff.films.filter { it.name != null && it.rating != null && it.genres.isNotEmpty() }

            val filmsByRole = filteredFilms.groupBy { it.profession }
            val roles = filmsByRole.keys.toList()
            val selectedTabIndex = remember { mutableStateOf(0) }

            val selectedRole = roles.getOrNull(selectedTabIndex.value)

            val filmsForSelectedRole = filmsByRole[selectedRole] ?: emptyList()


            Scaffold(
                topBar = {
                    Header(onBackClick = {
                        filmographyPageViewModel.onEvent(
                            FilmographyIntent.NavigateToBack(
                                navigateToBack = {
                                    navController.popBackStack()
                                }
                            )
                        )
                    })
                }
            ) { paddingValues ->



                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(top = 10.dp, bottom = 80.dp, start = 26.dp, end = 26.dp),

                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    item{
                        Text(
                            text = staff.name.orEmpty(),
                            fontSize = 18.sp
                        )
                    }
                    item {
                        LazyRow(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            roles.forEachIndexed { index, role ->
                                item {
                                    RoleTab(
                                        text =  "$role ${filmsByRole[role]?.size}",
                                        isSelected = selectedTabIndex.value == index,
                                        onClick = { selectedTabIndex.value = index }
                                    )
                                }
                            }


                        }
                    }
                    items(minOf(filmsForSelectedRole.size, 30)) { index ->
                        FilmCard(filmsForSelectedRole[index], onClick = {
                            filmographyPageViewModel.onEvent(FilmographyIntent.NavigateToFilmDetail(
                                navigateToFilmDetail = {
                                    navController.navigate(FilmDetailRoute.FilmDetail.passId(filteredFilms[index].id.toString()))
                                }
                            ))
                        })
                    }

                }
            }
        }
        is FilmographyUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }





}


@Composable
fun RoleTab(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.height(36.dp)
            .clickable(onClick = onClick)
            .background (if (isSelected) mainColor else Color.Transparent, shape = RoundedCornerShape(14.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color.White else mainColor,
                shape = RoundedCornerShape(14.dp)
            )
    ){
        Text(
            text = text,
            color = if (isSelected) Color.White else mainColor,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun FilmCard(film: StaffFilm, onClick: () -> Unit){
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back),
                    contentDescription = null,
                    tint = mainColor
                )
            }
        },
        title = {
            Text(
                text = "Фильмография",
                fontSize = 18.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}
