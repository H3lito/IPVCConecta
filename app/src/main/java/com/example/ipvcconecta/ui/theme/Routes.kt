package com.example.ipvcconecta.ui.theme

import kotlinx.serialization.Serializable
@Serializable
object LoginRoute

@Serializable
object RegisterRouter

@Serializable
object MapRoute

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
object AdicionarLocalRoute

@Serializable
object FavoritoRoute

@Serializable
object PerfilRoute