package com.example.ipvcconecta.ui.theme.components.createAcc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel: ViewModel() {
    private val _uiState= MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    //-------------
    fun onChangedName(newValue: String){
        _uiState.update{it.copy(nome= newValue)}
    }
    // função para mudar email
    fun onEmailChanged(newValue: String){
        _uiState.update {it.copy(email = newValue)}
    }

    // -------
    fun onPasswordChanged(newValue: String){
        _uiState.update{it.copy(password = newValue)}
    }
    // -----------------
    fun onConfirmPassChanged(newValue: String){
        _uiState.update{it.copy(confirmPass = newValue)}
    }

    //------------------------
    fun registeraccount(){
        val state = uiState.value
        if(state.nome.isBlank() || state.email.isBlank() || state.password.isBlank() || state.confirmPass.isBlank()){
            _uiState.update { it.copy(ErrorMessage = "Obrigatório Preencher todos os campos" ) }
            return
        }
        if (state.password != state.confirmPass){
            _uiState.update { it.copy(ErrorMessage = "As palavras-passe são diferentes" ) }
            return
        }

        // Sem implementação do retrofit

        _uiState.update {it.copy(Sucess= true, ErrorMessage = null )}

    }
}