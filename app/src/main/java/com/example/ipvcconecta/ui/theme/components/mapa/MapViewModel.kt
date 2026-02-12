package com.example.ipvcconecta.ui.theme.components.mapa
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipvcconecta.ui.theme.components.Datas.repository.LocaisRepository
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class MapViewModel(application: Application) : AndroidViewModel(application) {

    // Ligar ao Repositório
    private val repository = LocaisRepository(application)

    // ESTADOS DO MAPA
    private val _cameraLocation = MutableStateFlow<LatLng?>(null)
    val cameraLocation: StateFlow<LatLng?> = _cameraLocation

    private val _searchResultLocation = MutableStateFlow<LatLng?>(null)
    val searchResultLocation: StateFlow<LatLng?> = _searchResultLocation

    private val _searchResultTitle = MutableStateFlow("")
    val searchResultTitle = _searchResultTitle.asStateFlow()

    // Conversão de flow para stateflow
    // O 'stateIn' converte o fluxo da Base de Dados num Estado sempre atualizado
    val locais: StateFlow<List<LocalDetalhe>> = repository.locais
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    init {
        // Ao abrir o mapa, tenta sincronizar (Fire-and-forget)
        atualizarDados()

        // Pede localização GPS
        getDeviceLocation()
    }

    // Repositório decide se vai á internet ou não( lógica do offline-first)
    private fun atualizarDados() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.syncLocais()
        }
    }

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
                // Pesquisa na lista atual (que veio da BD)
                val localEncontrado = locais.value.find { local ->
                    local.nome.contains(query, ignoreCase = true) ||
                            local.categoria.contains(query, ignoreCase = true)
                }

                if (localEncontrado != null) {
                    val newLatLng = LatLng(
                        localEncontrado.latitude,
                        localEncontrado.longitude
                    )
                    // Atualiza o estado e desloca para onde está o marcador
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