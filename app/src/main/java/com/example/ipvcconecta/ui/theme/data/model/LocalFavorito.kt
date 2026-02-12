package com.example.ipvcconecta.ui.theme.data.model

// Modelo de Dados
data class LocalFavorito(
    val id: String = "",
    val nome: String,
    val categoria: String,
    val descricao: String = "",
    val horario: String = "",
    // Garantir que não dê crash
    val latitude: Double? = null,
    val longitude: Double? = null
)
