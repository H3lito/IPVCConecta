package com.example.ipvcconecta.ui.theme.components.perfil

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel : ViewModel() {

    private val _nome = MutableStateFlow("Nome do Utilizador")
    val nome: StateFlow<String> = _nome

    private val _email = MutableStateFlow("email@ipvc.pt")
    val email: StateFlow<String> = _email

    fun logout() {
        // mais tarde: FirebaseAuth.signOut()
    }
}
