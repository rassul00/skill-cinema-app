package com.example.skillcinemaapp.presentation.period

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.presentation.common.SharedDataViewModel
import com.example.skillcinemaapp.presentation.ui.app.mainColor
import java.time.LocalDate


@Composable
fun PeriodPage(navController: NavController, periodPageViewModel: PeriodPageViewModel = hiltViewModel(), sharedDataViewModel: SharedDataViewModel) {

    val uiState by periodPageViewModel.periodUiState.collectAsState()

    val fromPer by sharedDataViewModel.fromPer.collectAsState()

    val toPer by sharedDataViewModel.toPer.collectAsState()

    when(uiState){
        is PeriodUiState.Success -> {


            var startYear = 1998
            val minYear = 1900
            val maxYear = LocalDate.now().year
            val yearsPerPage = 12


            var fromCurrentStartYear by remember { mutableStateOf(startYear) }
            val fromCurrentEndYear = fromCurrentStartYear + yearsPerPage - 1
            val fromYears = (fromCurrentStartYear..fromCurrentEndYear).filter { it <= maxYear }

            var toCurrentStartYear by remember { mutableStateOf(startYear) }
            val toCurrentEndYear = toCurrentStartYear + yearsPerPage - 1
            val toYears = (toCurrentStartYear..toCurrentEndYear).filter { it <= maxYear }



            var yearFrom by remember { mutableStateOf(fromPer) }
            var yearTo by remember { mutableStateOf(toPer) }

            Scaffold(
                topBar = {
                    Header("Период")
                }
            ) { paddingValues ->

                LazyColumn(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxSize()
                        .padding(horizontal = 26.dp),
                    contentPadding = paddingValues
                ) {

                    item{
                        Text(
                            text = "Искать с в период с $yearFrom",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.padding(bottom = 15.dp))
                    }

                    item {

                        Column(
                            modifier = Modifier
                                .padding(bottom = 25.dp)
                                .fillMaxWidth()
                                .height(270.dp)
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "$fromCurrentStartYear - $fromCurrentEndYear",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = mainColor
                                )

                                Row{
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.back),
                                        contentDescription = "Previous Years",
                                        modifier = Modifier.clickable {
                                            fromCurrentStartYear = (fromCurrentStartYear - yearsPerPage).coerceAtLeast(minYear)
                                        }
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.right),
                                        contentDescription = "Next Years",
                                        modifier = Modifier.clickable {
                                            fromCurrentStartYear = (fromCurrentStartYear + yearsPerPage).coerceAtMost(maxYear - yearsPerPage + 1)
                                        }
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                fromYears.chunked(3).forEach { yearRow ->
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        yearRow.forEach { year ->
                                            TextButton(onClick = {
                                                if (year <= yearTo) {
                                                    yearFrom = year
                                                }
                                            },
                                                colors = ButtonDefaults.textButtonColors(
                                                    contentColor = if (year == yearFrom) mainColor else Color.Black
                                                )
                                            ) {
                                                Text(
                                                    text = year.toString(),
                                                    fontSize = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item{
                        Text(
                            text = "Искать в период до $yearTo",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.padding(bottom = 15.dp))
                    }


                    item {


                        Column(
                            modifier = Modifier
                                .padding(bottom = 25.dp)
                                .fillMaxWidth()
                                .height(265.dp)
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "$toCurrentStartYear - $toCurrentEndYear",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = mainColor
                                )

                                Row{
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.back),
                                        contentDescription = "Previous Years",
                                        modifier = Modifier.clickable {
                                            toCurrentStartYear = (toCurrentStartYear - yearsPerPage).coerceAtLeast(minYear)
                                        }
                                    )

                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.right),
                                        contentDescription = "Next Years",
                                        modifier = Modifier.clickable {
                                            toCurrentStartYear = (toCurrentStartYear + yearsPerPage).coerceAtMost(maxYear - yearsPerPage + 1)
                                        }
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                toYears.chunked(3).forEach { yearRow ->
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        yearRow.forEach { year ->
                                            TextButton(onClick = {
                                                if (year >= yearFrom) {
                                                    yearTo = year
                                                }
                                            },
                                                colors = ButtonDefaults.textButtonColors(
                                                    contentColor = if (year == yearTo) mainColor else Color.Black
                                                )
                                            ) {
                                                Text(
                                                    text = year.toString(),
                                                    fontSize = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                sharedDataViewModel.updateFromPer(yearFrom)
                                sharedDataViewModel.updateToPer(yearTo)
                                periodPageViewModel.onEvent(PeriodIntent.NavigateToBack(
                                    navigateToBack = {
                                        navController.popBackStack()
                                    }
                                ))
                            },
                                colors = ButtonDefaults.textButtonColors(
                                containerColor = mainColor,
                                contentColor = Color.White
                            )) {
                                Text(
                                    text = "Выбрать"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(text: String){
    CenterAlignedTopAppBar(
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


