package com.example.skillcinemaapp.presentation.staff

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import com.example.skillcinemaapp.data.model.Staff
import com.example.skillcinemaapp.data.model.StaffFilm
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.home.LabelRating
import com.example.skillcinemaapp.presentation.home.ShowAllButton
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute
import com.example.skillcinemaapp.presentation.navigation.StaffRoute
import com.example.skillcinemaapp.presentation.ui.app.genreTextColor
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor

@Composable
fun StaffPage(navController: NavController, staffPageViewModel: StaffPageViewModel = hiltViewModel()){
    val uiState by staffPageViewModel.staffUiState.collectAsState()

    when(uiState){
        is StaffUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is StaffUiState.Success -> {
            val uiState = uiState as StaffUiState.Success
            Scaffold(
                topBar = {
                    Header(uiState.staff, onBackClick = {
                        staffPageViewModel.onEvent(
                            StaffIntent.NavigateToBack(
                                navigateToBack = {
                                    navController.popBackStack()
                                }
                            )
                        )
                    })
                }
            ){ paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(top = 10.dp, bottom = 30.dp, start = 26.dp, end = 26.dp),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ){

                    item{
                        StaffInfo(staff = uiState.staff)
                    }
                    item{
                        BestFilms(navController = navController, staffPageViewModel = staffPageViewModel, films = uiState.staff.films)
                    }
                    item{
                        Filmography(staff = uiState.staff,
                            onClick = {
                                staffPageViewModel.onEvent(StaffIntent.NavigateToFilmography(
                                    navigateToFilmography = {
                                        navController.navigate(StaffRoute.Filmography.passId(uiState.staff.id.toString()))
                                    }
                                ))
                            }
                        )
                    }
                }
            }
        }
        is StaffUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(staff: Staff, onBackClick: () -> Unit) {
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
                text = if (staff.name != null) staff.name else staff.nameEn.orEmpty(),
                fontSize = 18.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}


@Composable
fun StaffInfo(staff: Staff){

    Row(
        modifier = Modifier.fillMaxWidth().height(201.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(model = staff.poster),
            contentDescription = null,
            modifier = Modifier.height(201.dp).width(146.dp).clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
        )
        Column {
            Text(
                text = if(staff.name != null) staff.name else staff.nameEn.orEmpty(),
                fontSize = 16.sp
            )

            Text(
                text = staff.profession,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun BestFilms(navController: NavController, staffPageViewModel: StaffPageViewModel, films: List<StaffFilm>){

    val filteredFilms = films.filter { it.name != null && it.rating != null && it.genres.isNotEmpty()}.sortedByDescending { it.rating }
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Лучшее",
            fontSize = 18.sp,
            fontWeight = W500
        )
        Row(
            modifier = Modifier.clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Все",
                fontSize = 14.sp,
                color = mainColor
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.right),
                tint = mainColor,
                contentDescription = null
            )
        }



    }
    LazyHorizontalGrid(
        modifier = Modifier
            .height(200.dp),
        rows = GridCells.Fixed(1)
    ) {
        items(minOf(filteredFilms.size, 8)) { index ->
            FilmCard(filteredFilms[index], onClick = {
                staffPageViewModel.onEvent(StaffIntent.NavigateToFilmDetail(
                    navigateToFilmDetail = {
                        navController.navigate(FilmDetailRoute.FilmDetail.passId(filteredFilms[index].id.toString()))
                    }
                ))
            })
        }
        item{
            ShowAllButton {  }
        }

    }
}

@Composable
fun Filmography(staff: Staff, onClick: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column{
            Text(
                text = "Фильмография",
                fontSize = 18.sp,
                fontWeight = W500
            )

            Text(
                text = "${staff.films.size} фильма",
                fontSize = 18.sp,
                fontWeight = W500
            )
        }

        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "К списку",
                fontSize = 16.sp,
                fontWeight = W400,
                color = mainColor
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.right),
                tint = mainColor,
                contentDescription = null
            )
        }


    }
}

@Composable
fun FilmCard(film: StaffFilm, onClick: () -> Unit){
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
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            film.rating?.let { LabelRating(it) }
        }

        Text(
            text = film.name.orEmpty(),
            fontSize = 14.sp,
            fontWeight = W400,
            color = textColor
        )

        Text(
            text = film.genres[0].genre,
            fontSize = 14.sp,
            fontWeight = W400,
            color = genreTextColor
        )
    }
}