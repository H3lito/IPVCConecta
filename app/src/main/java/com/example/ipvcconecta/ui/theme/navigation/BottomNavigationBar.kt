package com.example.ipvcconecta.ui.theme.navigation


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ipvcconecta.ui.theme.shett

// Barra de navegação Inferior
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    // Obter os dados
    val items = getBottomNavItems()
    // Observar o estado de navegação
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //Interface Declarativa
    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    // Gestão de memória e Pilha
                    // Reaproveitamento da instancia
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