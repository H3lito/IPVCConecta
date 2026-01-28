package com.example.ipvcconecta.ui.theme.navigation

import AuthViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ipvcconecta.ui.theme.DetalheLocalRoute
import com.example.ipvcconecta.ui.theme.ExplorarRoute
import com.example.ipvcconecta.ui.theme.FavoritoRoute
import com.example.ipvcconecta.ui.theme.ListaLocaisRoute
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.MapRoute
import com.example.ipvcconecta.ui.theme.PerfilRoute
import com.example.ipvcconecta.ui.theme.RegisterRouter
import com.example.ipvcconecta.ui.theme.components.createAcc.RegisterScreen
import com.example.ipvcconecta.ui.theme.components.explorar.ExplorarScreen
import com.example.ipvcconecta.ui.theme.components.favoritos.FavoritosScreen
import com.example.ipvcconecta.ui.theme.components.locais.DetalheLocalScreen
import com.example.ipvcconecta.ui.theme.components.locais.ListaLocaisScreen
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.example.ipvcconecta.ui.theme.components.login.LoginScreen
import com.example.ipvcconecta.ui.theme.components.mapa.MapScreen
import com.example.ipvcconecta.ui.theme.components.perfil.PerfilScreen


@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    authViewModel: AuthViewModel
) {

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = Modifier.padding(padding),

        //) {
        builder = {

            // ---------- AUTH ----------
            composable<LoginRoute> {
                LoginScreen(
                    authViewModel = authViewModel,
                    /*onLoginSuccess = { navController.navigate(MapRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },*/
                    onGoToRegister = {
                        navController.navigate(RegisterRouter)
                    }
                )
            }


            composable<RegisterRouter> {
                RegisterScreen(
                    authViewModel = authViewModel,
                    /* onRegisterClick = { navController.navigate(MapRoute) {
                        popUpTo(RegisterRouter) { inclusive = true }
                    }
                },*/
                    onGoToLogin = {
                        //navController.popBackStack()
                        navController.navigate(LoginRoute){
                            popUpTo(RegisterRouter) { inclusive = true }
                        }
                    }
                )
            }

            // ---------- MAIN ----------
            composable<MapRoute> {
                MapScreen(
                    onNavigateToAddLocation = {
                        // Supondo que tens uma rota para adicionar local:
                        navController.navigate("AdicionarLocalRoute") // Ou o teu Objeto @Serializable correspondente
                    }
                )
            }
            composable<ExplorarRoute> {
                ExplorarScreen { categoria ->
                    navController.navigate(ListaLocaisRoute(categoria))
                }
            }

            composable<ListaLocaisRoute> { backStackEntry ->
                val categoria =
                    backStackEntry.arguments?.getString("categoria") ?: ""

                ListaLocaisScreen(
                    categoria = categoria,
                    onLocalClick = { local ->
                        navController.navigate(
                            DetalheLocalRoute(local.nome)
                        )
                    }
                )
            }

            composable<DetalheLocalRoute> { backStackEntry ->
                val nome =
                    backStackEntry.arguments?.getString("nome") ?: ""

                // MOCK DO LOCAL (mais tarde vem do ViewModel)
                val localMock = LocalDetalhe(
                    nome = nome,
                    categoria = "Alimentação",
                    descricao = "Descrição do local selecionado.",
                    morada = "Av. do Atlântico, Viana do Castelo",
                    horario = "Seg–Sex: 12h–14h"

                )

                DetalheLocalScreen(
                    local = localMock,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable<FavoritoRoute> {
                FavoritosScreen(
                    onLocalClick = { local ->
                        navController.navigate(
                            DetalheLocalRoute(local.nome)
                        )
                    }
                )
            }


            composable<PerfilRoute> {
                PerfilScreen(

                )
            }
        }
    )
    }



