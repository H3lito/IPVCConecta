package com.example.ipvcconecta.ui.theme.navigation

import android.R.attr.label
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.shett

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = getBottomNavItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true

                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = shett
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = shett
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}