package com.example.ipvcconecta.ui.theme.navigation

import AuthViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ipvcconecta.ui.theme.AdicionarLocalRoute
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
import com.example.ipvcconecta.ui.theme.components.favoritos.FavoritosViewModel
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
    val favoritosViewModel: FavoritosViewModel = viewModel()

    // Ler a lista de favoritos
    val listaFavoritos by favoritosViewModel.favoritos.collectAsState()

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
            composable<MapRoute> { backStackEntry ->
                // Extrair os argumentos da rota (Type-Safe)
                val args = backStackEntry.toRoute<MapRoute>()

                MapScreen(
                    focusLat = args.lat,
                    focusLng = args.lng,
                    onNavigateToAddLocation = {
                        navController.navigate(AdicionarLocalRoute)
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
                val nome = backStackEntry.arguments?.getString("nome") ?: ""

                val localReal = com.example.ipvcconecta.ui.theme.components.locais.LocaisData
                    .carregarLocaisIniciais()
                    .find { it.nome == nome }

                if (localReal != null) {
                    // Verificar se este local específico está na lista de favoritos
                    val isFav = listaFavoritos.any { it.nome == localReal.nome }

                    DetalheLocalScreen(
                        local = localReal,
                        isFavorito = isFav, // <--- Passamos o estado
                        onBackClick = { navController.popBackStack() },

                        // Lógica do botão FAVORITO
                        onFavoritoClick = {
                            favoritosViewModel.toggleFavorito(localReal)
                        },

                        // Lógica do botão MAPA (como falámos antes)
                        onVerMapaClick = {
                            navController.navigate(
                                MapRoute(lat = localReal.latitude, lng = localReal.longitude)
                            ) { launchSingleTop = true }
                        }
                    )
                }
            }
            composable<FavoritoRoute> {
                FavoritosScreen(
                    viewModel = favoritosViewModel, // Passar o mesmo ViewModel
                    onLocalClick = { local ->
                        navController.navigate(DetalheLocalRoute(local.nome))
                    }
                )
            }


            // ---------- PERFIL ----------
            composable<PerfilRoute> {
                PerfilScreen(
                    // 1. Ação do Botão Favoritos
                    onFavoritosClick = {
                        // Navega para o separador dos Favoritos
                        navController.navigate(FavoritoRoute) {
                            launchSingleTop = true
                        }
                    },

                    // 2. Ação do Botão Sair
                    onLogoutClick = {
                        // Navega para o Login
                        navController.navigate(LoginRoute) {
                            // Limpa tudo o que estava para trás (Mapa, Perfil, etc.)
                            // para o utilizador não conseguir voltar atrás
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            }
    )}




