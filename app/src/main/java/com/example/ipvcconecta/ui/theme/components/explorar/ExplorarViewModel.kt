package com.example.ipvcconecta.ui.theme.components.explorar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExplorarViewModel : ViewModel() {

    private val _categorias = MutableStateFlow(
        listOf(
            "Alimentação",
            "Alojamento",
            "Transportes",
            "Serviços",
            "Lazer"
        )
    )

    val categorias: StateFlow<List<String>> = _categorias
}
