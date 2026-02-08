package com.example.ipvcconecta

import com.example.ipvcconecta.ui.theme.AuthViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ipvcconecta.ui.theme.AuthState
import com.example.ipvcconecta.ui.theme.ExplorarRoute
import com.example.ipvcconecta.ui.theme.FavoritoRoute
import com.example.ipvcconecta.ui.theme.IPVCConectaTheme
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.MapRoute
import com.example.ipvcconecta.ui.theme.PerfilRoute
import com.example.ipvcconecta.ui.theme.RegisterRouter
import com.example.ipvcconecta.ui.theme.navigation.BottomNavigationBar
import com.example.ipvcconecta.ui.theme.navigation.NavHostContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //val navController = rememberNavController()

            IPVCConectaTheme( darkTheme = false) {
                Scaffolding()

                /*Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                )*/ /*{ padding ->
                    NavHostContainer(
                        navController = navController,
                        padding = padding
                    )
                }*/
            }
        }
    }
}

@Composable
fun Scaffolding() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.observeAsState()

    // 1. Obter a entrada atual da pilha de navegação
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // 2. Lógica CORRETA para Type-Safe Navigation
    // A barra aparece APENAS se estivermos num destes ecrãs principais:
    val showBottomBar = currentDestination?.hasRoute<MapRoute>() == true ||
            currentDestination?.hasRoute<ExplorarRoute>() == true ||
            currentDestination?.hasRoute<FavoritoRoute>() == true ||
            currentDestination?.hasRoute<PerfilRoute>() == true

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            navController.navigate(MapRoute()) {
                popUpTo(LoginRoute) { inclusive = true }    // Usa o objeto da rota, não string
                popUpTo(RegisterRouter) { inclusive = true } // Usa o objeto da rota
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        },
        content = { padding ->
            NavHostContainer(
                navController = navController,
                padding = padding,
                authViewModel = authViewModel
            )
        }
    )
}