package com.example.ipvcconecta.ui.theme.components.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    //Função atualização campo email

    fun onChangeEmail(newEmail: String){
        _uiState.value = _uiState.value.copy(email= newEmail)
    }

    //Função para atualização campo password

    fun onChangedPassword(newPassword: String){
        _uiState.value = _uiState.value.copy(password = newPassword)

    }

    //Lógica do login

    fun login(): Boolean{
        return uiState.value.email.isNotBlank() && uiState.value.password.isNotBlank()
    }

    private fun MutableStateFlow<LoginUiState>.update(function: Any){}
}