package com.example.skillcinemaapp.presentation.film_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.model.Film
import com.example.skillcinemaapp.data.model.FilmDetailImage
import com.example.skillcinemaapp.data.model.FilmDetailSimilarFilm
import com.example.skillcinemaapp.data.model.FilmDetailStaff
import com.example.skillcinemaapp.data.room.Collection
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.home.LabelRating
import com.example.skillcinemaapp.presentation.navigation.FilmDetailRoute
import com.example.skillcinemaapp.presentation.navigation.StaffRoute
import com.example.skillcinemaapp.presentation.ui.app.genreTextColor
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import com.example.skillcinemaapp.presentation.ui.app.textColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "FrequentlyChangedStateReadInComposition")
@Composable
fun FilmDetailPage(
    navController: NavController,
    filmDetailPageViewModel: FilmDetailPageViewModel = hiltViewModel()
) {
    val uiState by filmDetailPageViewModel.filmDetailUiState.collectAsState()

    when(uiState){
        is FilmDetailUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is FilmDetailUiState.Success -> {
            val uiState = uiState as FilmDetailUiState.Success

            val starList = uiState.filmDetailStaffs.filter { it.professionKey == "ACTOR" }
            val workWithList = uiState.filmDetailStaffs.filter { it.professionKey != "ACTOR" }


            val scrollState = rememberLazyListState()

            val isAppBarVisible by remember {
                derivedStateOf { scrollState.firstVisibleItemIndex > 0 }
            }


            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    item {
                        FilmDescription(navController, filmDetailPageViewModel, uiState.film, uiState.collections)
                    }
                    item {
                        AboutFilm(uiState.film)
                    }
                    item {
                        FilmStars(navController, filmDetailPageViewModel, starList)
                    }
                    item {
                        WorkWithFilm(navController, filmDetailPageViewModel, workWithList)
                    }
                    item {
                        GalleryFilm(
                            uiState.images,
                            onClick = {
                                filmDetailPageViewModel.onEvent(
                                    FilmDetailIntent.NavigateToGallery(
                                        navigateToGallery = {
                                            navController.navigate(FilmDetailRoute.Gallery.passId(uiState.film.id.toString()))
                                        }
                                    )
                                )
                            }
                        )
                    }
                    item {
                        SimilarFilms(uiState.filmDetailSimilarFilms)
                    }
                }
                if (isAppBarVisible) {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                filmDetailPageViewModel.onEvent(
                                    FilmDetailIntent.NavigateToBack(
                                        navigateToBack = {
                                            navController.popBackStack()
                                        }
                                    )
                                )
                            }) {
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
                                text = uiState.film.name.orEmpty(),
                                fontSize = 18.sp
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White
                        )
                    )
                }

            }

        }
        is FilmDetailUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDescription(navController: NavController, filmDetailViewModel: FilmDetailPageViewModel, film: Film, collections: List<Collection>) {

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    val isFilmInCollection by filmDetailViewModel.isFilmInCollection.collectAsState(emptyMap())

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
    ) {


        Image(
            painter = rememberAsyncImagePainter(model = if (film.cover != null) film.cover else R.drawable.ic_launcher),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1B1B1B),
                            Color(0x001B1B1B)
                        ),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f
                    )
                )
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Text(
                text = if(film.nameOriginal != null) film.nameOriginal else film.name.orEmpty(),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                letterSpacing = 2.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            val ageLimit = film.ageLimit?.removePrefix("age")

            Text(
                text = "${film.rating} ${film.name}\n" +
                        "${film.year}, ${film.genres[0].genre}\n" +
                        "${film.countries[0].country}, ${ageLimit}+",
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center

            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 13.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.padding(start = 60.dp))


                val isInFavorite = isFilmInCollection[filmDetailViewModel.favoriteCollectionId] ?: false

                IconBut(onClick = {
                    if (isInFavorite){
                        filmDetailViewModel.onEvent(FilmDetailIntent.DeleteFilmFromCollection(
                            collectionId = filmDetailViewModel.favoriteCollectionId,
                            filmId = film.id
                        ))
                    }
                    else{
                        filmDetailViewModel.onEvent(FilmDetailIntent.SaveFilmToCollection(
                            collectionId = filmDetailViewModel.favoriteCollectionId,
                            film = film
                        ))
                    }
                },
                    R.drawable.like,
                    isInFavorite
                )


                val isInWatchLater = isFilmInCollection[filmDetailViewModel.watchLaterCollectionId] ?: false

                IconBut(onClick = {
                    if (isInWatchLater){
                        filmDetailViewModel.onEvent(FilmDetailIntent.DeleteFilmFromCollection(
                            collectionId = filmDetailViewModel.watchLaterCollectionId,
                            filmId = film.id
                        ))
                    }
                    else{
                        filmDetailViewModel.onEvent(FilmDetailIntent.SaveFilmToCollection(
                            collectionId = filmDetailViewModel.watchLaterCollectionId,
                            film = film
                        ))
                    }
                }, R.drawable.favorite,
                    isInWatchLater
                )

                IconBut({}, R.drawable.hide, false)

                IconBut({}, R.drawable.share, false )

                IconBut(onClick = {
                    isSheetOpen = true
                }, R.drawable.options, false)
                Spacer(modifier = Modifier.padding(end = 60.dp))
            }
        }

        IconButton(
            onClick = {
                filmDetailViewModel.onEvent(
                    FilmDetailIntent.NavigateToBack(
                        navigateToBack = {
                            navController.popBackStack()
                        }
                    )
                )
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 25.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.back),
                tint = Color.White,
                contentDescription = null
            )
        }

    }

    var showDialog by remember { mutableStateOf(false) }

    if(isSheetOpen){
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            },
            containerColor = Color.White
        ){

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 26.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                FilmCard(
                    film = film
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.plus_collection),
                        contentDescription = null
                    )
                    Text(
                        text = "Добавить свою коллекцию",
                        fontSize = 16.sp
                    )
                }


                collections.forEach{ collection ->

                    val isInCollection = isFilmInCollection[collection.id] ?: false

                    var isChecked by remember { mutableStateOf(isInCollection) }

                    Row(
                        modifier = Modifier.padding(start = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                if (isChecked){
                                    filmDetailViewModel.onEvent(FilmDetailIntent.DeleteFilmFromCollection(
                                        collectionId = collection.id,
                                        filmId = film.id
                                    ))
                                }
                                else{
                                    filmDetailViewModel.onEvent(FilmDetailIntent.SaveFilmToCollection(
                                        collectionId = collection.id,
                                        film = film
                                    ))
                                }
                                isChecked = !isChecked
                            },
                            modifier = Modifier.size(25.dp)
                        ){
                            Icon(
                                imageVector = if (isChecked) {
                                    ImageVector.vectorResource(id = R.drawable.check_mark)
                                } else {
                                    ImageVector.vectorResource(id = R.drawable.place_for_mark)
                                },
                                contentDescription = null
                            )
                        }
                        Text(
                            text = collection.name,
                            fontSize = 16.sp
                        )


                        Spacer(Modifier.weight(1f))

                        Text(
                            text = collection.collectionSize.toString(),
                            fontSize = 16.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(start = 24.dp),
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
            }

        }
    }


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
                        filmDetailViewModel.onEvent(FilmDetailIntent.AddNewCollection(
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

}





@Composable
fun AboutFilm(film: Film){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(start = 26.dp, end = 26.dp)
    ){
        film.shorDes?.let {
            Text(text = it, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        film.fullDes?.let {
            Text(text = film.fullDes, fontSize = 16.sp)
        }
    }
}


@Composable
fun IconBut(onClick: () -> Unit, icon: Int, isInCollection: Boolean){

    IconButton(onClick = onClick ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            tint = if (isInCollection) mainColor else Color.White ,
            modifier = Modifier.size(24.dp)
        )
    }
}



@Composable
fun FilmStars(navController: NavController, filmDetailViewModel: FilmDetailPageViewModel, filmDetailStaffs: List<FilmDetailStaff>){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, end = 26.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = "В фильме снимались" ,
            fontSize = 18.sp,
            fontWeight = W500
        )
        Row(
            modifier = Modifier
                .clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically,

        ){
            Text(
                text = filmDetailStaffs.size.toString(),
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

    LazyHorizontalGrid(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp)
            .height(300.dp),
        rows = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(minOf(filmDetailStaffs.size, 20)) { index ->
            StaffCard(navController, filmDetailViewModel, filmDetailStaffs[index], filmDetailStaffs[index].professionKey)
        }
    }
}





@Composable
fun WorkWithFilm(navController: NavController, filmDetailViewModel: FilmDetailPageViewModel, filmDetailStaffs: List<FilmDetailStaff>){
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 26.dp, end = 26.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Над фильмом работали" ,
            fontSize = 18.sp,
            fontWeight = W500

        )

        Row(
            modifier = Modifier.clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = filmDetailStaffs.size.toString(),
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
    LazyHorizontalGrid(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp)
            .height(164.dp),
        rows = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(minOf(filmDetailStaffs.size, 6)) { index ->
            StaffCard(navController, filmDetailViewModel, filmDetailStaffs[index], filmDetailStaffs[index].professionKey)
        }
    }
}


@Composable
fun GalleryFilm(images: List<FilmDetailImage>, onClick: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 26.dp, end = 26.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Галерея",
            fontSize = 18.sp,
            fontWeight = W500
        )


        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = images.size.toString(),
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

    LazyHorizontalGrid(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp)
            .height(136.dp),
        rows = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(minOf(images.size, 6)) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(108.dp)
                    .width(192.dp)
                    .clip(RoundedCornerShape(4.dp)),
                elevation = CardDefaults.elevatedCardElevation(2.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = images[index].image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()

                )
            }
            Spacer(modifier = Modifier.padding(end = 10.dp))
        }
    }
}



@Composable
fun SimilarFilms(filmDetailSimilarFilms: List<FilmDetailSimilarFilm>){

    val filteredFilms = filmDetailSimilarFilms.filter { it.name != null && it.rating != null && it.genres.isNotEmpty() }


    Row(modifier = Modifier.fillMaxWidth().padding(start = 26.dp, end = 26.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Похожие фильмы",
            fontSize = 18.sp,
            fontWeight = W500
        )


        Row(
            modifier = Modifier.clickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = filmDetailSimilarFilms.size.toString(),
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
    LazyHorizontalGrid(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp)
            .height(200.dp),
        rows = GridCells.Fixed(1)
    ) {
        items(minOf(filteredFilms.size, 4)) { index ->
            FilmCard(filteredFilms[index], onClick = {})
        }
    }
}


@Composable
fun StaffCard(navController: NavController, filmDetailViewModel: FilmDetailPageViewModel, filmDetailStaff: FilmDetailStaff, description: String){
    Row(modifier = Modifier
        .width(207.dp)
        .height(68.dp)
        .clickable(onClick = {
            filmDetailViewModel.onEvent(
                FilmDetailIntent.NavigateToStaff(
                    navigateToStaff = {
                        navController.navigate(StaffRoute.Staff.passId(filmDetailStaff.id.toString()))
                    }
                )
            )
        }),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = rememberAsyncImagePainter(model = filmDetailStaff.poster),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .width(49.dp)
                .height(68.dp),

            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding(end = 16.dp))

        Column{
            Text(
                text = if(filmDetailStaff.name != null) filmDetailStaff.name else filmDetailStaff.nameEn.orEmpty(),
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = description,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun FilmCard(film: FilmDetailSimilarFilm, onClick: () -> Unit){
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


@Composable
fun FilmCard(film: Film){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp),
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