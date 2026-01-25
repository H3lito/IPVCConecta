package com.example.ipvcconecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ipvcconecta.ui.theme.IPVCConectaTheme
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.RegisterRouter
import com.example.ipvcconecta.ui.theme.components.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            IPVCConectaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute,
                        modifier = Modifier.padding(innerPadding)){
                        composable<LoginRoute>{
                            //LoginScreen(navController)
                        }

                     }



                }
            }
        }
    }
}

