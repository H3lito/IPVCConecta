import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    // --- FUNÇÃO DE REGISTO CORRIGIDA ---
    fun signup(nome: String, email: String, password: String, confirmPass: String) {
        if (nome.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            _authState.value = AuthState.Error("All fields are required")
            return
        }
        if (password != confirmPass) {
            _authState.value = AuthState.Error("Passwords do not match!")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password must have at least 6 characters")
            return
        }

        _authState.value = AuthState.Loading

        // 1. Criar a conta
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 2. A conta foi criada! Agora vamos GRAVAR O NOME.
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(nome) // <-- Aqui guardamos o nome
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Nome guardado com sucesso
                                _authState.value = AuthState.Authenticated
                            } else {
                                // Conta criada, mas nome falhou (raro, mas autentica na mesma)
                                _authState.value = AuthState.Authenticated
                            }
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

// Mantém a classe AuthState igual cá em baixo...
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}