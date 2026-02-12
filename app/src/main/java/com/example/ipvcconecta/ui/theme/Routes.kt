package com.example.ipvcconecta.ui.theme

import kotlinx.serialization.Serializable

// Define o grafo da navegação da aplicação utilizando abordagem Type-Safe
// Os objectos são serializáveis o que garante a segurança em tempo de compilação impedindo a navegação entre os ecrãs sem passar os argumentos obrigatórios

// Rotas Estáticas
@Serializable
object LoginRoute

@Serializable
object RegisterRouter
@Serializable
object ExplorarRoute

@Serializable
object FavoritoRoute

@Serializable
object PerfilRoute
@Serializable
object GuiaSouNovoRoute

// Rotas Dinâmicas(com os argumentos)
@Serializable
data class MapRoute(
    val lat: Double? = null,
    val lng: Double? = null
)



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


// O Type-Safe Navigation garante a segurança em tempo de compilação