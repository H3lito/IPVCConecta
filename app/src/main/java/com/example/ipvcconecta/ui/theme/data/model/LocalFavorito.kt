package com.example.ipvcconecta.ui.theme.data.model


data class LocalFavorito(
    val id: String = "",
    val nome: String,
    val categoria: String,
    val descricao: String = "",
    val horario: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
)
