package com.example.ipvcconecta.ui.theme.components.perfil

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PerfilViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance() // <--- Acesso ao Storage

    // Estados da UI
    private val _nome = MutableStateFlow("Utilizador")
    private val _email = MutableStateFlow("email@ipvc.pt")
    private val _fotoUri = MutableStateFlow<Uri?>(null)
    private val _isLoading = MutableStateFlow(false) // Para mostrar um spinner enquanto carrega

    val nome: StateFlow<String> = _nome.asStateFlow()
    val email: StateFlow<String> = _email.asStateFlow()
    val fotoUri: StateFlow<Uri?> = _fotoUri.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        carregarDadosUtilizador()
    }

    private fun carregarDadosUtilizador() {
        val user = auth.currentUser
        if (user != null) {
            _nome.value = user.displayName ?: "Utilizador"
            _email.value = user.email ?: "Sem email"
            _fotoUri.value = user.photoUrl // O Firebase Auth já guarda o link da foto!
        }
    }

    fun atualizarFoto(uri: Uri?) {
        if (uri == null) return
        val user = auth.currentUser ?: return

        _isLoading.value = true // Começa a carregar

        // 1. Definir onde guardar a imagem na nuvem
        // Pasta: profile_images / Nome: ID_do_User.jpg
        val storageRef = storage.reference.child("profile_images/${user.uid}.jpg")

        // 2. Fazer o Upload
        storageRef.putFile(uri)
            .addOnSuccessListener {
                // 3. Upload feito! Agora pedimos o Link de Download (URL público)
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->

                    // 4. Atualizar o Perfil do Auth com esse novo Link
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setPhotoUri(downloadUri)
                        .build()

                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Tudo correu bem! Atualizamos a UI.
                                _fotoUri.value = downloadUri
                                _isLoading.value = false
                            }
                        }
                }
            }
            .addOnFailureListener {
                // Se falhar o upload
                _isLoading.value = false
                it.printStackTrace()
            }
    }

    fun logout() {
        auth.signOut()
    }
}