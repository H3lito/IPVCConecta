package com.example.ipvcconecta.ui.theme.components.Datas

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe

@Entity(tableName = "locais_table")
data class LocalEntity(
    @PrimaryKey val id: String,
    val nome: String,
    val categoria: String,
    val descricao: String,
    val morada: String,
    val horario: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toDetalhe(): LocalDetalhe {
        return LocalDetalhe(
            id = this.id,
            nome = this.nome,
            categoria = this.categoria,
            descricao = this.descricao,
            morada = this.morada,
            horario = this.horario,
            latitude = this.latitude,
            longitude = this.longitude
        )
    }
}