package com.example.ipvcconecta.ui.theme.components.createAcc

data class RegisterUiState(
    val nome: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPass: String = "",
    val Sucess: Boolean = false, //Spinner
    val ErrorMessage: String?= null // validação
)