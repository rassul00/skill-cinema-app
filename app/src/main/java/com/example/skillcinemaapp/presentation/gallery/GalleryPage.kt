package com.example.skillcinemaapp.presentation.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.ui.app.mainColor

@Composable
fun GalleryPage(navController: NavController, galleryPageViewModel: GalleryPageViewModel = hiltViewModel()) {

    val uiState by galleryPageViewModel.galleryUiState.collectAsState()

    when (uiState) {
        is GalleryUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is GalleryUiState.Success -> {
            val uiState = uiState as GalleryUiState.Success


            Scaffold(
                topBar = {
                    Header(
                        onBackClick = {
                            galleryPageViewModel.onEvent(
                                GalleryIntent.NavigateToBack(
                                    navigateToBack = {
                                        navController.popBackStack()
                                    }
                                )
                            )
                        }
                    )
                }
            ) { paddingValues ->

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth().background(color = Color.White).padding(start = 26.dp, end = 26.dp, top = 16.dp, bottom = 70.dp),
                    contentPadding = paddingValues,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(uiState.images.size) { index ->
                        GalleryImageCard(
                            imageUrl = uiState.images[index].image
                        )
                    }
                }
            }
        }
        is GalleryUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
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
                    tint = mainColor,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            }
        },
        title = {
            Text(
                text = "Галерея",
                fontSize = 18.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun GalleryImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .height(95.dp)
            .width(170.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

