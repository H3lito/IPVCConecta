package com.example.ipvcconecta.ui.theme.components.mapa
import android.app.Application
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipvcconecta.ui.theme.components.locais.LocaisData
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class MapViewModel(application: Application) : AndroidViewModel(application) {

    // --- 1. ESTADOS DO MAPA ---
    private val _cameraLocation = MutableStateFlow<LatLng?>(null)
    val cameraLocation: StateFlow<LatLng?> = _cameraLocation

    private val _searchResultLocation = MutableStateFlow<LatLng?>(null)
    val searchResultLocation: StateFlow<LatLng?> = _searchResultLocation

    private val _searchResultTitle = MutableStateFlow<String>("")
    val searchResultTitle: StateFlow<String> = _searchResultTitle

    // --- 2. DADOS (Lista de Locais em Memória) ---
    private val _locais = MutableStateFlow<List<LocalDetalhe>>(emptyList())
    val locais: StateFlow<List<LocalDetalhe>> = _locais
    // Cliente de Localização
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)


    init {
        // Agora carregamos a partir do ficheiro externo organizado
        _locais.value = LocaisData.carregarLocaisIniciais()
    }



    // Adicionar local à lista em memória (Simulação do FAB)
    fun adicionarLocal(local: LocalDetalhe) {
        val listaAtual = _locais.value.toMutableList()
        listaAtual.add(local)
        _locais.value = listaAtual
    }

    // --- RESTANTE CÓDIGO (Location e Search) MANTÉM-SE IGUAL ---
    @android.annotation.SuppressLint("MissingPermission")
    fun getDeviceLocation() {
        try {
            val locationResult = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            )
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    val newLatLng = LatLng(location.latitude, location.longitude)
                    _cameraLocation.value = newLatLng
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun searchLocation(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val localEncontrado = _locais.value.find { local ->
                    local.nome.contains(query, ignoreCase = true) ||
                            local.categoria.contains(query, ignoreCase = true)
                }

                if (localEncontrado != null) {
                    val newLatLng = com.google.android.gms.maps.model.LatLng(
                        localEncontrado.latitude,
                        localEncontrado.longitude
                    )

                    _cameraLocation.value = newLatLng
                    _searchResultLocation.value = newLatLng
                    _searchResultTitle.value = localEncontrado.nome
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}