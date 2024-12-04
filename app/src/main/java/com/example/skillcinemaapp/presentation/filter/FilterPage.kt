package com.example.skillcinemaapp.presentation.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skillcinemaapp.presentation.common.SharedDataViewModel
import com.example.skillcinemaapp.presentation.list_film.Header
import com.example.skillcinemaapp.presentation.navigation.FilterRoute
import com.example.skillcinemaapp.presentation.ui.app.mainColor


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterPage(navController: NavController, filterPageViewModel: FilterPageViewModel = hiltViewModel(), sharedDataViewModel: SharedDataViewModel) {

    val uiState by filterPageViewModel.filterUiState.collectAsState()

    val type by sharedDataViewModel.type.collectAsState()

    val country by sharedDataViewModel.country.collectAsState()
    val countryId by sharedDataViewModel.countryId.collectAsState()

    val genre by sharedDataViewModel.genre.collectAsState()
    val genreId by sharedDataViewModel.genreId.collectAsState()

    val fromPer by sharedDataViewModel.fromPer.collectAsState()
    val toPer by sharedDataViewModel.toPer.collectAsState()

    val ratingFrom by sharedDataViewModel.ratingFrom.collectAsState()
    val ratingTo by sharedDataViewModel.ratingTo.collectAsState()

    val order by sharedDataViewModel.order.collectAsState()

    when(uiState){
        is FilterUiState.Success -> {


            var selectedType by remember { mutableStateOf(type) }

            var rating by remember { mutableStateOf(ratingFrom..ratingTo) }

            var selectedOrder by remember { mutableStateOf(order) }


            Scaffold(
                topBar = { Header("Настройки поиска", onBackClick = {
                    filterPageViewModel.onEvent(FilterIntent.NavigateToBack(
                        navigateToBack = {
                            navController.popBackStack()
                        }
                    ))
                }) }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 26.dp),
                    contentPadding = paddingValues,
                ) {

                    item {
                        SectionTitle("Показывать")
                        SelectionRow(
                            options = listOf("Все", "Фильмы", "Сериалы"),
                            selectedOption = selectedType,
                            onOptionSelected = {
                                selectedType = it
                                sharedDataViewModel.updateType(it)
                            }
                        )
                        Spacer(Modifier.padding(bottom = 25.dp))
                    }

                    item {
                        FilterSelector(
                            label = "Страна",
                            value = country,
                            onClick = {
                                filterPageViewModel.onEvent(FilterIntent.NavigateToCountry(
                                    navigateToCountry = {
                                        navController.navigate(FilterRoute.Country.route)
                                    }
                                ))
                            }
                        )
                        Spacer(Modifier.padding(bottom = 25.dp))
                    }
                    item {
                        FilterSelector(
                            label = "Жанр",
                            value = genre,
                            onClick = {
                                filterPageViewModel.onEvent(FilterIntent.NavigateToCountry(
                                    navigateToCountry = {
                                        navController.navigate(FilterRoute.Genre.route)
                                    }
                                ))
                            }
                        )
                        Spacer(Modifier.padding(bottom = 25.dp))
                    }
                    item {
                        FilterSelector(
                            label = "Год",
                            value = "с $fromPer до $toPer",
                            onClick = {
                                filterPageViewModel.onEvent(FilterIntent.NavigateToCountry(
                                    navigateToCountry = {
                                        navController.navigate(FilterRoute.Period.route)
                                    }
                                ))
                            }
                        )
                        Spacer(Modifier.padding(bottom = 25.dp))

                    }
                    item {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
                        ) {
                            Text(text = "Рейтинг", fontSize = 16.sp)
                            Text(
                                text = "от ${rating.start.toInt()} до ${rating.endInclusive.toInt()}",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        RangeSlider(
                            value = rating,
                            onValueChange = {
                                sharedDataViewModel.updateRatingFrom(it.start)
                                sharedDataViewModel.updateRatingTo(it.endInclusive)
                                rating = it },
                            valueRange = 1f..10f,
                            onValueChangeFinished = {
                            },
                            steps = 8,
                            startThumb = {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = CircleShape
                                        )
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                )
                            },
                            endThumb = {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = CircleShape
                                        )
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                )
                            },
                            track = { sliderState ->
                                val totalRange =
                                    sliderState.valueRange.endInclusive - sliderState.valueRange.start
                                val startFraction =
                                    (sliderState.activeRangeStart - sliderState.valueRange.start) / totalRange
                                val endFraction =
                                    (sliderState.activeRangeEnd - sliderState.valueRange.start) / totalRange

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .weight(maxOf(startFraction, 0.001f))
                                            .height(4.dp)
                                            .background(
                                                color = Color.LightGray,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .weight(endFraction - startFraction)
                                            .height(4.dp)
                                            .background(
                                                color = mainColor,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .weight(maxOf(1f - endFraction, 0.001f))
                                            .height(4.dp)
                                            .background(
                                                color = Color.LightGray,
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )
                                }
                            }
                        )
                        Spacer(Modifier.padding(bottom = 15.dp))
                    }

                    item {
                        SectionTitle("Сортировать")
                        SelectionRow(
                            options = listOf("Дата", "Популярность", "Рейтинг"),
                            selectedOption = selectedOrder,
                            onOptionSelected = {
                                selectedOrder = it
                                sharedDataViewModel.updateOrder(it)
                            }
                        )
                    }

                    item{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                filterPageViewModel.onEvent(FilterIntent.NavigateToFilmsByFilter(
                                    navigateToFilmsByFilter = {

                                        navController.navigate(
                                            FilterRoute.FilmsByFilter.passFilter(
                                                country = country,
                                                countryId = countryId.toString(),
                                                genre = genre,
                                                genreId = genreId.toString(),
                                                order =  if(selectedOrder == "Дата") "YEAR" else if (selectedOrder == "Популярность") "NUM_VOTE" else "RATING",
                                                type =  if(selectedType == "Все") "ALL" else if (selectedType == "Фильмы") "FILM" else "TV_SERIES",
                                                ratingFrom = rating.start.toInt().toString(),
                                                ratingTo = rating.endInclusive.toInt().toString(),
                                                fromPer = fromPer.toString(),
                                                toPer = toPer.toString()
                                            )
                                        )
                                    }
                                ))
                            },
                                colors = ButtonDefaults.textButtonColors(
                                containerColor = mainColor,
                                contentColor = Color.White
                            )) {
                                Text(
                                    text = "Показать"
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        color = Color.LightGray
    )
    Spacer(Modifier.padding(bottom = 10.dp))
}


@Composable
fun SelectionRow(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(31.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(26.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = selectedOption == option
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = if (index == 0) 26.dp else 0.dp,
                            bottomStart = if (index == 0) 26.dp else 0.dp,
                            topEnd = if (index == options.lastIndex) 26.dp else 0.dp,
                            bottomEnd = if (index == options.lastIndex) 26.dp else 0.dp
                        )
                    )
                    .border(0.5.dp, Color.Black)
                    .background(if (isSelected) mainColor else Color.Transparent)
                    .clickable { onOptionSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
fun FilterSelector(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp)
        Text(text = value, fontSize = 16.sp, color = Color.Gray)
    }
}

//@Composable
//fun Divider() {
//    Box(
//        modifier = Modifier
//            .padding(top = 20.dp)
//            .fillMaxWidth()
//            .height(1.dp)
//            .background(Color.LightGray)
//    )
//}






