package com.example.ipvcconecta.ui.theme.components.mapa
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.PrimaryDark

import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    focusLat: Double? = null,
    focusLng: Double? = null,
    viewModel: MapViewModel = viewModel(),
    onNavigateToAddLocation: () -> Unit = {}
) {
    val context = LocalContext.current
    val locais by viewModel.locais.collectAsState()

    // Estados do mapa
    val cameraLocation by viewModel.cameraLocation.collectAsState()
    val searchResultLocation by viewModel.searchResultLocation.collectAsState()
    val searchResultTitle by viewModel.searchResultTitle.collectAsState()

    // Permissões
    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.getDeviceLocation()
    }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.getDeviceLocation()
        }
    }

    val defaultLocation = LatLng(41.6932, -8.8329)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }

    // Atualiza câmara
    LaunchedEffect(cameraLocation) {
        // CORREÇÃO: Só move para a tua localização SE NÃO houver um foco definido
        if (focusLat == null && focusLng == null) {
            cameraLocation?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 16f)
            }
        }
    }

    LaunchedEffect(focusLat, focusLng) {
        if (focusLat != null && focusLng != null) {
            val posicaoFoco = LatLng(focusLat, focusLng)
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(posicaoFoco, 18f), // Zoom 18 para ver bem perto
                durationMs = 1500
            )
        }
    }

    // --- CORREÇÃO DO ESTILO ---
    // Usamos 'remember' para não recriar o objeto a cada frame
    // O JSON remove: POI (Pontos de Interesse), Transit (Paragens) e Road Icons (Símbolos de estrada)
    val mapStyleOptions = remember {
        MapStyleOptions(
            "[" +
                    "{ \"featureType\": \"poi\", \"stylers\": [ { \"visibility\": \"off\" } ] }," +
                    "{ \"featureType\": \"transit\", \"stylers\": [ { \"visibility\": \"off\" } ] }," +
                    "{ \"featureType\": \"road\", \"elementType\": \"labels.icon\", \"stylers\": [ { \"visibility\": \"off\" } ] }" +
                    "]"
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            MapHeader()

            SearchBar(
                onSearch = { query ->
                    viewModel.searchLocation(query)
                }
            )

            GoogleMap(
                modifier = Modifier.weight(1f),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermission, // Mantém a tua posição (ponto azul)
                    mapStyleOptions = mapStyleOptions            // <--- O ESTILO APLICA-SE AQUI
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    mapToolbarEnabled = false
                )
            ) {
                // Os teus marcadores do IPVC
                locais.forEach { local ->
                    Marker(
                        state = MarkerState(position = LatLng(local.latitude, local.longitude)),
                        title = local.nome,
                        snippet = local.categoria,
                        icon = com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                            com.example.ipvcconecta.ui.theme.components.mapa.MapUtils.getMarkerIcon(local.categoria)
                        )
                    )
                }

                // Marcador de pesquisa
                searchResultLocation?.let { loc ->
                    Marker(
                        state = MarkerState(position = loc),
                        title = searchResultTitle,
                        icon = com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                            com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
                        )
                    )
                }
            }
        }

        AddLocationFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                // Simulação de adicionar
                val centroMapa = cameraPositionState.position.target
                val novoLocal = LocalDetalhe("Novo Ponto", "Utilizador", "Criado pelo FAB", "Localizado no mapa", "Sempre aberto", centroMapa.latitude, centroMapa.longitude)
                viewModel.adicionarLocal(novoLocal)
                Toast.makeText(context, "Ponto adicionado!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

// --- Componentes Auxiliares (Header, SearchBar, FAB) ---

@Composable
fun MapHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "IPVCConecta",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryDark
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Pesquisar...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(query)
                keyboardController?.hide()
            })
        )
    }
}

@Composable
fun AddLocationFAB(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(16.dp),
        containerColor = Primary
    ) {
        Icon(Icons.Default.Add, contentDescription = "Adicionar local", tint = Color.White)
    }}