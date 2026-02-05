package com.example.ipvcconecta.ui.theme.components.Datas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {
    // Devolve uma lista que se atualiza sozinha (Flow)
    @Query("SELECT * FROM favoritos")
    fun getFavoritos(): Flow<List<FavoritoEntity>>

    // Verifica se já existe (para pintar o coração)
    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE nome = :nome LIMIT 1)")
    suspend fun isFavorito(nome: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorito(favorito: FavoritoEntity)

    @Delete
    suspend fun deleteFavorito(favorito: FavoritoEntity)
}