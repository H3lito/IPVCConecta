package com.example.ipvcconecta.ui.theme.components.mapa
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // --- 1. ESTADOS DO MAPA (GPS e Pesquisa) ---
    private val _cameraLocation = MutableStateFlow<LatLng?>(null)
    val cameraLocation: StateFlow<LatLng?> = _cameraLocation

    private val _searchResultLocation = MutableStateFlow<LatLng?>(null)
    val searchResultLocation: StateFlow<LatLng?> = _searchResultLocation

    private val _searchResultTitle = MutableStateFlow<String>("")
    val searchResultTitle: StateFlow<String> = _searchResultTitle

    // --- 2. ESTADOS DOS DADOS (Lista de Locais) ---
    // Era isto que te faltava para o erro desaparecer!
    private val _locais = MutableStateFlow<List<LocalDetalhe>>(emptyList())
    val locais: StateFlow<List<LocalDetalhe>> = _locais

    // Cliente de Localização (GPS)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    // --- FUNÇÕES DO MAPA ---

    @SuppressLint("MissingPermission")
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

    fun searchLocation(query: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                @Suppress("DEPRECATION")
                val results = geocoder.getFromLocationName(query, 1)

                if (!results.isNullOrEmpty()) {
                    val location = results[0]
                    val searchLatLng = LatLng(location.latitude, location.longitude)

                    _cameraLocation.value = searchLatLng
                    _searchResultLocation.value = searchLatLng
                    _searchResultTitle.value = query
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --- FUNÇÕES DOS DADOS (Adicionar Local) ---

    // Esta é a função que o teu ecrã de "Adicionar" está à procura!
    fun adicionarLocal(local: LocalDetalhe) {
        // Adiciona o novo local à lista atual
        _locais.value = _locais.value + local
    }
}