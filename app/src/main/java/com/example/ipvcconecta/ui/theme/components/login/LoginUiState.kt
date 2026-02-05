package com.example.ipvcconecta.ui.theme.components.login

data class LoginUiState(
    val email: String = "",
    val password: String= "",
    val isLoading: Boolean = false, // Spinner
    val ErrorMessage: String? = null
)