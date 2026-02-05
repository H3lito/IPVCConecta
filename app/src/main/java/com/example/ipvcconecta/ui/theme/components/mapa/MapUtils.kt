package com.example.ipvcconecta.ui.theme.components.mapa

import com.google.android.gms.maps.model.BitmapDescriptorFactory

object MapUtils {

    fun getMarkerIcon(categoria: String): Float {
        return when (categoria) {
            "Escola", "Serviços Académicos" -> BitmapDescriptorFactory.HUE_AZURE   // Azul
            "Alimentação", "Restaurante", "Bar" -> BitmapDescriptorFactory.HUE_ORANGE // Laranja
            "Alojamento", "Residência" -> BitmapDescriptorFactory.HUE_VIOLET       // Roxo
            "Transportes" -> BitmapDescriptorFactory.HUE_GREEN                     // Verde
            "Saúde" -> BitmapDescriptorFactory.HUE_ROSE                            // Rosa
            "Lazer", "Desporto" -> BitmapDescriptorFactory.HUE_YELLOW              // Amarelo
            else -> BitmapDescriptorFactory.HUE_RED                                // Vermelho (Padrão)
        }
    }
}