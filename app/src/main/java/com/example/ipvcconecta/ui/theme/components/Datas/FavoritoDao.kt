package com.example.ipvcconecta.ui.theme.components.Datas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {
    // ⚠️ ALTERADO: Agora pedimos apenas os favoritos DESTE utilizador
    @Query("SELECT * FROM favoritos WHERE userId = :userId")
    fun getFavoritos(userId: String): Flow<List<FavoritoEntity>>

    // ⚠️ ALTERADO: Verificamos se ESTE utilizador tem este favorito
    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE nome = :nome AND userId = :userId LIMIT 1)")
    suspend fun isFavorito(nome: String, userId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorito(favorito: FavoritoEntity)

    @Delete
    suspend fun deleteFavorito(favorito: FavoritoEntity)


    // --- CACHE DO MAPA (Mantém-se igual, é global para todos) ---
    @Query("SELECT * FROM locais_table")
    fun getAllLocais(): Flow<List<LocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocais(locais: List<LocalEntity>)
}