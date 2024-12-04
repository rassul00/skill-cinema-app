package com.example.skillcinemaapp.presentation.country

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.data.model.FilterCountry
import com.example.skillcinemaapp.presentation.common.ErrorPage
import com.example.skillcinemaapp.presentation.common.LoadingPage
import com.example.skillcinemaapp.presentation.common.SharedDataViewModel
import com.example.skillcinemaapp.presentation.ui.app.mainColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CountryPage(navController: NavController, countryPageViewModel: CountryPageViewModel = hiltViewModel(), sharedDataViewModel: SharedDataViewModel) {


    val uiState by countryPageViewModel.countryUiState.collectAsState()

    val country by sharedDataViewModel.country.collectAsState()

    val countryId by sharedDataViewModel.countryId.collectAsState()

    var selectedCountry by remember { mutableStateOf(country) }

    var selectedCountryId by remember { mutableStateOf(countryId) }

    var searchQuery by remember { mutableStateOf("") }

    var filteredCountries by remember { mutableStateOf(listOf<FilterCountry>()) }





    when(uiState){
        is CountryUiState.Loading -> LoadingPage(modifier = Modifier.fillMaxSize())
        is CountryUiState.Success -> {
            val uiState = uiState as CountryUiState.Success


            filteredCountries = if (searchQuery.isEmpty()) {
                uiState.countries
            } else {
                uiState.countries.filter {
                    it.country.contains(searchQuery, ignoreCase = true)
                }
            }

            Scaffold(
                topBar = {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(top = 40.dp, bottom = 10.dp, start = 26.dp, end = 26.dp)
                            .fillMaxWidth()
                    ){
                        Box(
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                                .fillMaxWidth()
                        ){

                            IconButton(onClick = {
                                sharedDataViewModel.updateCountry(selectedCountry)
                                sharedDataViewModel.updateCountryId(selectedCountryId)
                                countryPageViewModel.onEvent(CountryIntent.NavigateToBack(
                                    navigateToBack = {
                                        navController.popBackStack()
                                    }
                                ))
                            },
                                modifier = Modifier.size(25.dp)){
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.back),
                                    contentDescription = null,
                                    tint = mainColor,
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                )
                            }

                            Text(
                                text = "Страна",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE5E5E5), shape = RoundedCornerShape(26.dp))
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.search_icon), tint = Color.Gray, contentDescription = null)


                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
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
                                    Text(text = "Введите страну", color = Color.Gray, style = TextStyle(fontSize = 14.sp))
                                },
                                modifier = Modifier.weight(1f)
                            )

                        }

                    }
                }
            ){ paddingValues ->

                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(Color.White).padding(top = 20.dp, bottom = 80.dp, start = 26.dp, end = 26.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = paddingValues,
                ){
                    items(filteredCountries.size){ index ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clickable(onClick = {

                                    selectedCountry = filteredCountries[index].country
                                    selectedCountryId = filteredCountries[index].id.toString()
                                })
                        ){
                            Text(
                                text = filteredCountries[index].country,
                                color = if (selectedCountry == filteredCountries[index].country) mainColor else Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            }
        }

        is CountryUiState.Error -> ErrorPage(modifier = Modifier.fillMaxSize())
    }
}


