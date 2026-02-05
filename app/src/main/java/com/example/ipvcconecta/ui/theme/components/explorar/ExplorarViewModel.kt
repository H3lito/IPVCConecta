package com.example.ipvcconecta.ui.theme.components.explorar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExplorarViewModel : ViewModel() {

    private val _categorias = MutableStateFlow(
        listOf(
            "Escolas",
            "Alimentação",
            "Alojamento",
            "Transportes",
            "Serviços"

        )
    )

    val categorias: StateFlow<List<String>> = _categorias
}
