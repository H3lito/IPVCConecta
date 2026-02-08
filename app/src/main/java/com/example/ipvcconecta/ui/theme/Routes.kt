package com.example.ipvcconecta.ui.theme

import kotlinx.serialization.Serializable
@Serializable
object LoginRoute

@Serializable
object RegisterRouter

@Serializable
data class MapRoute(
    val lat: Double? = null,
    val lng: Double? = null
)

@Serializable
object ExplorarRoute

@Serializable
data class ListaLocaisRoute(
    val categoria: String
)
@Serializable
data class DetalheLocalRoute(
    val nome: String
)

@Serializable
data class AdicionarLocalRoute(
    val lat: Double,
    val lng: Double
)
@Serializable
object FavoritoRoute

@Serializable
object PerfilRoute
@Serializable
object GuiaSouNovoRoute