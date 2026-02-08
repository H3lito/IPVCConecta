package com.example.ipvcconecta.ui.theme.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ipvcconecta.ui.theme.ExplorarRoute
import com.example.ipvcconecta.ui.theme.FavoritoRoute
import com.example.ipvcconecta.ui.theme.MapRoute
import com.example.ipvcconecta.ui.theme.PerfilRoute


data class BottomNavItem(
    val title: String,
    val route: Any,
    val icon: ImageVector
)

fun getBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem("Mapa", MapRoute(), Icons.Filled.Map),
        BottomNavItem("Explorar", ExplorarRoute, Icons.Filled.Explore),
        BottomNavItem("Favoritos", FavoritoRoute, Icons.Filled.Favorite),
        BottomNavItem("Perfil", PerfilRoute, Icons.Filled.PersonPin))


}