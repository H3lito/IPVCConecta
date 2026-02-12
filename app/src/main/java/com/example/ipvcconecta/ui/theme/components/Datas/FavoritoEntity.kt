package com.example.ipvcconecta.ui.theme.components.Datas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos", primaryKeys = ["nome", "userId"])
data class FavoritoEntity(
    val userId: String,
    val nome: String,
    val categoria: String,
    val descricao: String,
    val morada: String,
    val horario: String,
    val latitude: Double,
    val longitude: Double
)