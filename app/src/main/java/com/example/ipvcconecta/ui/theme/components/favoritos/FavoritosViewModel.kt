package com.example.ipvcconecta.ui.theme.components.favoritos

import androidx.lifecycle.ViewModel
import com.example.ipvcconecta.ui.theme.data.model.LocalFavorito
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritosViewModel : ViewModel() {

    private val _favoritos =
        MutableStateFlow<List<LocalFavorito>>(emptyList())

    val favoritos: StateFlow<List<LocalFavorito>> = _favoritos

    fun adicionarFavorito(local: LocalFavorito) {
        _favoritos.value = _favoritos.value + local
    }

    fun removerFavorito(local: LocalFavorito) {
        _favoritos.value = _favoritos.value - local
    }
}
