package com.example.skillcinemaapp.bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.skillcinemaapp.R
import com.example.skillcinemaapp.graph.PageRoute
import com.example.skillcinemaapp.ui.theme.mainColor


sealed class BottomBarItem(val route: String, val icon: Int) {
    data object Home : BottomBarItem(PageRoute.Home.route, R.drawable.home_icon)
    data object Search : BottomBarItem(PageRoute.Search.route, R.drawable.search_icon)
    data object Profile : BottomBarItem(PageRoute.Profile.route, R.drawable.profile_icon)
}


@Composable
fun BottomBar(navController: NavController) {

    val bottomBarItems = listOf(
        BottomBarItem.Home,
        BottomBarItem.Search,
        BottomBarItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .height(64.dp),
    ) {
        Spacer(modifier = Modifier.padding(start = 40.dp))

        bottomBarItems.forEach { item ->
            AddItems(item, currentDestination, navController)
        }

        Spacer(modifier = Modifier.padding(end = 40.dp))
    }


}

@Composable
fun RowScope.AddItems(
    item: BottomBarItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true

    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = item.icon),
                tint = if (isSelected) mainColor else colorResource(id = R.color.black),
                contentDescription = null
            )
        },
        selected = isSelected,
        onClick = {
            navController.navigate(item.route)
        }
    )
}
