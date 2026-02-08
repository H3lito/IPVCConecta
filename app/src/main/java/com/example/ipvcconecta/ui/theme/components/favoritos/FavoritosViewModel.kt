package com.example.ipvcconecta.ui.theme.components.favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipvcconecta.ui.theme.components.Datas.AppDatabase
import com.example.ipvcconecta.ui.theme.components.Datas.FavoritoEntity
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.favoritoDao()
    private val auth = FirebaseAuth.getInstance()

    // Estado reativo que guarda o ID do utilizador atual
    private val currentUserId = MutableStateFlow(auth.currentUser?.uid)

    // A lista de favoritos atualiza-se automaticamente se o utilizador mudar
    @OptIn(ExperimentalCoroutinesApi::class)
    val favoritos: StateFlow<List<LocalDetalhe>> = currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                // Se houver user, busca os favoritos dele
                dao.getFavoritos(userId).map { entities ->
                    entities.map { entity ->
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
            } else {
                // Se não houver user (logout), lista vazia
                flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Função para atualizar o ID quando fazes Login/Logout
    fun checkUser() {
        currentUserId.value = auth.currentUser?.uid
    }

    fun toggleFavorito(local: LocalDetalhe) {
        val uid = auth.currentUser?.uid ?: return // Se não tiver logado, não faz nada

        viewModelScope.launch(Dispatchers.IO) {
            // Verifica se já é favorito DESTE user
            val exists = dao.isFavorito(local.nome, uid)

            val entity = FavoritoEntity(
                userId = uid, // <--- Guardamos com o ID do user
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