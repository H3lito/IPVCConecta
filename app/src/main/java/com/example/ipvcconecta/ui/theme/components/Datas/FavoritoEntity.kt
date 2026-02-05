package com.example.ipvcconecta.ui.theme.components.Datas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey val nome: String, // O nome serve como ID único
    val categoria: String,
    val descricao: String,
    val morada: String,
    val horario: String,
    val latitude: Double,
    val longitude: Double
)