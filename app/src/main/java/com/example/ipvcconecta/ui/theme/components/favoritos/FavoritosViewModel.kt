package com.example.ipvcconecta.ui.theme.components.favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipvcconecta.ui.theme.components.Datas.AppDatabase
import com.example.ipvcconecta.ui.theme.components.Datas.FavoritoEntity
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.example.ipvcconecta.ui.theme.data.model.LocalFavorito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.favoritoDao()

    private val _favoritos = MutableStateFlow<List<LocalDetalhe>>(emptyList())
    val favoritos: StateFlow<List<LocalDetalhe>> = _favoritos

    init {
        // Observar a Base de Dados Automaticamente
        viewModelScope.launch {
            dao.getFavoritos().collect { entities ->
                // Converter Entity (BD) -> LocalDetalhe (UI)
                _favoritos.value = entities.map { entity ->
                    LocalDetalhe(
                        nome = entity.nome,
                        categoria = entity.categoria,
                        descricao = entity.descricao,
                        morada = entity.morada,
                        horario = entity.horario,
                        latitude = entity.latitude,
                        longitude = entity.longitude
                    )
                }
            }
        }
    }

    fun toggleFavorito(local: LocalDetalhe) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = dao.isFavorito(local.nome)

            val entity = FavoritoEntity(
                nome = local.nome,
                categoria = local.categoria,
                descricao = local.descricao,
                morada = local.morada,
                horario = local.horario,
                latitude = local.latitude,
                longitude = local.longitude
            )

            if (exists) {
                dao.deleteFavorito(entity)
            } else {
                dao.insertFavorito(entity)
            }
        }
    }
}