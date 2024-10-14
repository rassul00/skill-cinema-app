@file:OptIn(ExperimentalFoundationApi::class)

package com.example.skillcinemaapp.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.ui.theme.textButtonColor
import com.example.skillcinemaapp.ui.theme.textColor
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch




sealed class OnBoardingItem(val image: Int, val text: String) {
    object OnBoarding1 : OnBoardingItem(R.drawable.onboarding1, "Узнавай\nо премьерах")
    object OnBoarding2 : OnBoardingItem(R.drawable.onboarding2, "Создавай\nколлекции")
    object OnBoarding3 : OnBoardingItem(R.drawable.onboarding3, "Делись\nс друзьями")
}

val onboardingItems = listOf(
    OnBoardingItem.OnBoarding1,
    OnBoardingItem.OnBoarding2,
    OnBoardingItem.OnBoarding3
)



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPage(onClick: () -> Unit) {


    val pagerState = rememberPagerState{ onboardingItems.size }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 46.dp, horizontal = 26.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){


        item{
            Header(onClick)
        }

        item{
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
            ) { page ->
                OnBoardingPageImageAndText(onboardingItems[page])
            }
        }


        item{
            PagerIndicator(pagerState, coroutineScope)
        }
    }
}



@Composable
fun Header(onClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Skillcinema",
            color = textColor,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp
        )

        TextButton(onClick = onClick) {
            Text(
                text = "Пропустить",
                color = textButtonColor,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun OnBoardingPageImageAndText(page: OnBoardingItem) {
   Column{
       Image(
           painter = painterResource(id = page.image),
           contentDescription = null,
       )
       Text(
           text = page.text,
           color = textColor,
           fontSize = 32.sp,
           fontWeight = FontWeight.W500
       )
   }
}

@Composable
fun PagerIndicator(pagerState: PagerState, coroutineScope: CoroutineScope) {
    HorizontalPagerIndicator(
        pageCount = onboardingItems.size,
        pagerState = pagerState,
        modifier = Modifier
            .padding(bottom = 22.dp)
            .clickable {
                val nextPage =
                    if (pagerState.currentPage < onboardingItems.size - 1) {
                        pagerState.currentPage + 1
                    } else {
                        0
                    }
                coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
            },
        spacing = 4.dp
    )
}


