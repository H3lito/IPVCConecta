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
import com.example.ipvcconecta.ui.theme.GuiaSouNovoRoute
import com.example.ipvcconecta.ui.theme.ListaLocaisRoute
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.MapRoute
import com.example.ipvcconecta.ui.theme.PerfilRoute
import com.example.ipvcconecta.ui.theme.RegisterRouter
import com.example.ipvcconecta.ui.theme.components.createAcc.RegisterScreen
import com.example.ipvcconecta.ui.theme.components.explorar.ExplorarScreen
import com.example.ipvcconecta.ui.theme.components.favoritos.FavoritosScreen
import com.example.ipvcconecta.ui.theme.components.favoritos.FavoritosViewModel
import com.example.ipvcconecta.ui.theme.components.guia.GuiaSouNovoScreen
import com.example.ipvcconecta.ui.theme.components.locais.AdicionarLocalScreen
import com.example.ipvcconecta.ui.theme.components.locais.DetalheLocalScreen
import com.example.ipvcconecta.ui.theme.components.locais.ListaLocaisScreen
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
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
    // 1. Instanciar os ViewModels partilhados
    val favoritosViewModel: FavoritosViewModel = viewModel()
    val mapViewModel: MapViewModel = viewModel() // <--- NOVO: Precisamos disto para aceder aos dados reais

    // 2. Ler as listas vivas (Base de Dados / Firebase)
    val listaFavoritos by favoritosViewModel.favoritos.collectAsState()
    val todosLocais by mapViewModel.locais.collectAsState() // <--- A lista completa e atualizada

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = Modifier.padding(padding),
        builder = {

            // ---------- AUTH ----------
            composable<LoginRoute> {
                LoginScreen(
                    authViewModel = authViewModel,
                    onGoToRegister = {
                        navController.navigate(RegisterRouter)
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

            // ---------- MAIN ----------
            composable<MapRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<MapRoute>()

                MapScreen(
                    focusLat = args.lat,
                    focusLng = args.lng,
                    viewModel = mapViewModel, // <--- Passamos o mesmo ViewModel para partilhar dados
                    onNavigateToAddLocation = { lat, lng ->
                        // Recebe as coordenadas do botão FAB e navega
                        navController.navigate(AdicionarLocalRoute(lat, lng))
                    }
                )
            }
            composable<AdicionarLocalRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<AdicionarLocalRoute>()

                AdicionarLocalScreen(
                    lat = args.lat,
                    lng = args.lng,
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

                // AQUI ESTÁ A MAGIA:
                // Passamos 'todosLocais' (que vem do ViewModel lá de cima)
                // O ecrã a seguir vai filtrar sozinho.
                ListaLocaisScreen(
                    categoria = args.categoria,
                    locais = todosLocais, // <--- Passamos a lista do Room/Firebase
                    onLocalClick = { local ->
                        navController.navigate(DetalheLocalRoute(local.nome))
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // ⚠️ AQUI ESTAVA O PROBLEMA E AQUI ESTÁ A SOLUÇÃO ⚠️
            composable<DetalheLocalRoute> { backStackEntry ->
                val nomeRota = backStackEntry.arguments?.getString("nome") ?: ""

                // ANTES (ERRADO): Lia do ficheiro morto
                // val localReal = LocaisData.carregarLocaisIniciais().find { it.nome == nomeRota }

                // AGORA (CORRETO): Lê da lista viva do ViewModel
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


            // ---------- PERFIL ----------
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
                        navController.navigate(LoginRoute) {
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


