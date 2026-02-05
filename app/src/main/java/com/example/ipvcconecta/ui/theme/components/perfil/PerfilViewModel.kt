package com.example.ipvcconecta.ui.theme.components.perfil

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _nome = MutableStateFlow("Estudante IPVC")
    val nome: StateFlow<String> = _nome

    private val _email = MutableStateFlow(auth.currentUser?.email ?: "email@ipvc.pt")
    val email: StateFlow<String> = _email

    fun logout() {
        auth.signOut() // Desliga a sessão no Firebase
    }
}