package com.example.skillcinemaapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.room.LocalFilm
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.home.LabelRating
import com.example.skillcinemaapp.presentation.navigation.ProfileRoute
import com.example.skillcinemaapp.presentation.ui.app.genreTextColor
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor
import com.example.skillcinemaapp.data.room.Collection

@Composable
fun ProfilePage(navController: NavController, profilePageViewModel: ProfilePageViewModel = hiltViewModel()){

    val uiState by profilePageViewModel.profileUiState.collectAsState()
    

    when (uiState) {
        is ProfileUiState.Initial -> {
            profilePageViewModel.onEvent(ProfileIntent.Load)
        }
        is ProfileUiState.Success -> {
            val uiState = uiState as ProfileUiState.Success
            val films = uiState.films
            val collections = uiState.collections

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 70.dp, bottom = 80.dp, start = 26.dp, end = 26.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                item {
                    CategoryWithFilmsRow(profilePageViewModel, "Просмотрено" , films)
                }
                item {
                    Collection(navController = navController, profilePageViewModel = profilePageViewModel, collections = collections)
                }
            }
        }
        is ProfileUiState.Error -> {
            ErrorPage(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun CategoryWithFilmsRow(
    profilePageViewModel: ProfilePageViewModel,
    collectionName: String,
    films: List<LocalFilm>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = collectionName ,
            fontSize = 18.sp,
            fontWeight = W500

        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = films.size.toString(),
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

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(minOf(films.size, 8)) { index ->
            FilmCard(films[index])
        }
        item { ClearHistory(onClick = {
            profilePageViewModel.onEvent(ProfileIntent.ClearHistory(
                profilePageViewModel.viewedCollectionId
            ))
        })}
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Collection(
    navController: NavController,
    profilePageViewModel: ProfilePageViewModel,
    collections : List<Collection>){


    var showDialog by remember { mutableStateOf(false) }
    var collectionName by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(205.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = collectionName,
                    onValueChange = { collectionName = it },
                    placeholder = { Text("Придумайте название\nдля вашей новой коллекции") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .align(Alignment.TopStart),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 3
                )

                IconButton(
                    onClick = { showDialog = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.close),
                        contentDescription = "Close"
                    )
                }

                Button(
                    onClick = {
                        profilePageViewModel.onEvent(ProfileIntent.AddNewCollection(
                            collectionName
                        ))
                        showDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = mainColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text("Готово")
                }
            }
        }
    }

    Text(
        text = "Коллекции",
        fontSize = 18.sp,
        fontWeight = W500,
        color = textColor
    )
    Spacer(modifier = Modifier.padding(bottom = 10.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.size(25.dp)
        ){
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.add),
                contentDescription = null
            )
        }
        Text(
            text = "Cоздать свою коллекцию",
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.padding(bottom = 10.dp))

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){

        collections.forEach { collection ->
            if(collection.name != "Просмотрено"){
                Box(
                    modifier = Modifier
                        .size(146.dp)
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = {
                            profilePageViewModel.onEvent(ProfileIntent.NavigateToCollection(
                                navigateToCollection = {
                                    navController.navigate(ProfileRoute.Collection.passCollection(collection.id))
                                }
                            ))
                        })
                ) {
                    if (collection.name != "Любимое" && collection.name != "Хочу посмотреть") {
                        IconButton(
                            onClick = {
                                profilePageViewModel.onEvent(ProfileIntent.RemoveCollectionWithFilms(collection.id))
                            },
                            modifier = Modifier.align(Alignment.TopEnd).size(25.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.close),
                                contentDescription = "Delete Collection",
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (collection.name == "Любимое") {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.like),
                                contentDescription = null
                            )
                        } else if (collection.name == "Хочу посмотреть") {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.favorite),
                                contentDescription = null
                            )
                        }

                        Text(
                            text = collection.name,
                            fontSize = 16.sp
                        )
                        SizeOfFilms(collection.collectionSize)
                    }
                }
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .size(146.dp)
//                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
//                        .clickable(onClick = {
//                            profilePageViewModel.onEvent(ProfileIntent.NavigateToCollection(
//                                navigateToCollection = {
//                                    navController.navigate(ProfileRoute.Collection.passCollection(collection.id))
//                                }
//                            ))
//                        })
//                ){
//                    if(collection.name == "Любимое"){
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.like),
//                            contentDescription = null
//                        )
//                    }
//                    else if(collection.name == "Хочу посмотреть"){
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.favorite),
//                            contentDescription = null
//                        )
//                    }
//
//                    Text(
//                        text = collection.name,
//                        fontSize = 16.sp
//                    )
//                    SizeOfFilms(collection.collectionSize)
//
//                }
            }
        }

    }
}

@Composable
fun ClearHistory(onClick: () -> Unit) {
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
                imageVector = ImageVector.vectorResource(R.drawable.draft),
                tint = mainColor,
                contentDescription = null
            )
        }
        Text("Очистить\nисторию", fontSize = 12.sp, color = textColor, fontWeight = W400)
    }
}


@Composable
fun SizeOfFilms(rating: Int) {
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
fun FilmCard(film: LocalFilm) {
    Column(
        modifier = Modifier
            .width(111.dp)
            .padding(end = 8.dp)
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
        Text(film.genre.toString(), fontSize = 14.sp, fontWeight = W400, color = genreTextColor)
    }
}