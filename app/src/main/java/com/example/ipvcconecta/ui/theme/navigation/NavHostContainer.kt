package com.example.ipvcconecta.ui.theme.navigation

import com.example.ipvcconecta.ui.theme.AuthViewModel
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
import com.example.ipvcconecta.ui.theme.GuiaSouNovoRoute
import com.example.ipvcconecta.ui.theme.ListaLocaisRoute
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.MapRoute
import com.example.ipvcconecta.ui.theme.PerfilRoute
import com.example.ipvcconecta.ui.theme.RegisterRouter
import com.example.ipvcconecta.ui.theme.components.SplashScreen
import com.example.ipvcconecta.ui.theme.components.createAcc.RegisterScreen
import com.example.ipvcconecta.ui.theme.components.explorar.ExplorarScreen
import com.example.ipvcconecta.ui.theme.components.favoritos.FavoritosScreen
import com.example.ipvcconecta.ui.theme.components.favoritos.FavoritosViewModel
import com.example.ipvcconecta.ui.theme.components.guia.GuiaSouNovoScreen
import com.example.ipvcconecta.ui.theme.components.locais.AdicionarLocalScreen
import com.example.ipvcconecta.ui.theme.components.locais.DetalheLocalScreen
import com.example.ipvcconecta.ui.theme.components.locais.ListaLocaisScreen
import com.example.ipvcconecta.ui.theme.components.login.LoginScreen
import com.example.ipvcconecta.ui.theme.components.mapa.MapScreen
import com.example.ipvcconecta.ui.theme.components.mapa.MapViewModel
import com.example.ipvcconecta.ui.theme.components.perfil.PerfilScreen


@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    authViewModel: AuthViewModel
) {
    // 1. Instanciar os ViewModels no nível do NavHost
    val favoritosViewModel: FavoritosViewModel = viewModel()
    val mapViewModel: MapViewModel = viewModel()

    // 2. Observação Reactiva de Dados (StateFlow)
    // O collectAsState() para converter os fluxos assincronos
    val listaFavoritos by favoritosViewModel.favoritos.collectAsState()
    val todosLocais by mapViewModel.locais.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "splash", //ponto de entrada inicial
        modifier = Modifier.padding(padding),
        builder = {

            composable(route= "splash"){
                SplashScreen(navController = navController)
            }


            // Fluxo de Autenticação
            composable<LoginRoute> {
                LoginScreen(
                    authViewModel = authViewModel,
                    onGoToRegister = {
                        navController.navigate(RegisterRouter)
                    },
                    onLoginSuccess = {
                        navController.navigate(MapRoute()) {
                            // O popUpTo garante que o ecrã é destruído da memória, assim impedindo que o utlizador autenticado volte de volta ao ecrã de login
                            popUpTo(LoginRoute) {
                                inclusive = true
                            }
                        }
                    }
                )
            }


            composable<RegisterRouter> {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onGoToLogin = {
                        navController.navigate(LoginRoute){
                            popUpTo(RegisterRouter) { inclusive = true }
                        }
                    }
                )
            }

            // Fluxo Principal
            composable<MapRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<MapRoute>()

                MapScreen(
                    focusLat = args.lat,
                    focusLng = args.lng,
                    viewModel = mapViewModel,
                    onNavigateToAddLocation = { lat, lng ->
                        navController.navigate(AdicionarLocalRoute(lat, lng))
                    }
                )
            }
            composable<AdicionarLocalRoute> {
                AdicionarLocalScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable<ExplorarRoute> {
                ExplorarScreen { categoria ->
                    navController.navigate(ListaLocaisRoute(categoria))
                }
            }

            composable<ListaLocaisRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<ListaLocaisRoute>()

                // Injeção dos dados reativos.
                ListaLocaisScreen(
                    categoria = args.categoria,
                    locais = todosLocais,
                    onLocalClick = { local ->
                        navController.navigate(DetalheLocalRoute(local.nome))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<DetalheLocalRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<DetalheLocalRoute>()
                val nomeRota = args.nome
                // Resolução do Single Source Of Truth. Se o local for  atualizado no Firebase/Room, este ecrã reflete as mmudanças instantaneamente
                val localReal = todosLocais.find { it.nome == nomeRota }

                if (localReal != null) {
                    val isFav = listaFavoritos.any { it.nome == localReal.nome }

                    DetalheLocalScreen(
                        local = localReal,
                        isFavorito = isFav,
                        onBackClick = { navController.popBackStack() },

                        onFavoritoClick = {
                            favoritosViewModel.toggleFavorito(localReal)
                        },

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
                    viewModel = favoritosViewModel,
                    onLocalClick = { local ->
                        navController.navigate(DetalheLocalRoute(local.nome))
                    }
                )
            }


            // Fluxo Perfil e definições
            composable<PerfilRoute> {
                PerfilScreen(
                    onFavoritosClick = {
                        navController.navigate(FavoritoRoute) {
                            launchSingleTop = true
                        }
                    },
                    onGuiaClick = {
                        navController.navigate(GuiaSouNovoRoute)
                    },
                    onLogoutClick = {
                        authViewModel.signOut()
                        navController.navigate(LoginRoute) {
                            // Limpesa de segurança. Garante que não sobram ecrãs privados na memória após o logout
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            composable<GuiaSouNovoRoute> {
                GuiaSouNovoScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    )
}


