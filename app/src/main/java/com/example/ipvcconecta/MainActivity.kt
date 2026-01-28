package com.example.ipvcconecta

import AuthViewModel
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ipvcconecta.ui.theme.IPVCConectaTheme
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.MapRoute
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
    //---------------------------
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    //-----------------------

    //---------------
    val showBottomBar = currentRoute !in listOf("LoginRoute", "RegisterRoute")

    LaunchedEffect(authState){
        if(authState is AuthState.Authenticated){
            navController.navigate(MapRoute){
                popUpTo("LoginRoute"){ inclusive = true}
                popUpTo("RegisterRoute"){inclusive = true}
            }
        }
    }

    Scaffold(
        bottomBar = {
            if(showBottomBar) {
                BottomNavigationBar(navController = navController)
            }},
        content = { padding ->
            // Nav host: where screens are placed
            NavHostContainer(
                navController = navController,
                padding = padding,
                authViewModel = authViewModel
            )
        }
    )
}

