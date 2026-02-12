package com.example.ipvcconecta.ui.theme.components.locais

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddLocalViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uploadSuccess = MutableStateFlow(false)
    val uploadSuccess: StateFlow<Boolean> = _uploadSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun submeterLocal(nome: String, categoria: String, descricao: String, morada: String, horario: String) {

        if (nome.isBlank() || categoria.isBlank() || descricao.isBlank()) {
            _errorMessage.value = "Por favor, preenche todos os campos!"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        val novoLocal = hashMapOf(
            "nome" to nome,
            "categoria" to categoria,
            "descricao" to descricao,
            "morada" to morada,
            "horario" to horario,
            "status" to "pendente"

        )

        // Em vez de "locais", envia para "Sugestoes".
        // Assim, o mapa IGNORA estes dados até serem aprovados.
        db.collection("sugestoes")
            .document(nome)
            .set(novoLocal)
            .addOnSuccessListener {
                _isLoading.value = false
                _uploadSuccess.value = true
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                _errorMessage.value = "Erro ao guardar: ${e.message}"
            }
    }

    fun resetState() {
        _uploadSuccess.value = false
        _errorMessage.value = null
        _isLoading.value = false
    }
}