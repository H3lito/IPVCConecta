package com.example.ipvcconecta.ui.theme.components.mapa
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.example.ipvcconecta.ui.theme.shett
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    focusLat: Double? = null,
    focusLng: Double? = null,
    viewModel: MapViewModel = viewModel(),
    onNavigateToAddLocation: (Double, Double) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val locais by viewModel.locais.collectAsState()

    // Estados do controlo da camara e pesquisa
    val cameraLocation by viewModel.cameraLocation.collectAsState()
    val searchResultLocation by viewModel.searchResultLocation.collectAsState()
    val searchResultTitle by viewModel.searchResultTitle.collectAsState()

    // Estado para guardar o local selecionado
    var selectedLocation by remember { mutableStateOf<LocalDetalhe?>(null) }
    val sheetState = rememberModalBottomSheetState()

    // Permissões
    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.getDeviceLocation()
    }

    //Solicita a localização ou permissão
    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.getDeviceLocation()
        }
    }
// Controlo Imperativo da Camera
    val defaultLocation = LatLng(41.6932, -8.8329) // Viana do Castelo
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }

    // Lógica da Câmara- Centralizar no ultilizador ao encontrar a posição
    LaunchedEffect(cameraLocation) {
        if (focusLat == null && focusLng == null) {
            cameraLocation?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 16f)
            }
        }
    }
//Animação quando vê o ecrã detalhe
    LaunchedEffect(focusLat, focusLng) {
        if (focusLat != null && focusLng != null) {
            val posicaoFoco = LatLng(focusLat, focusLng)
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(posicaoFoco, 18f),
                durationMs = 1500
            )
        }
    }
// Estilização Json do Mapa
    // Remover os pontos de interesse irrelevantes da google
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
            // Componentes do google maps
            GoogleMap(
                modifier = Modifier.weight(1f),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermission,
                    mapStyleOptions = mapStyleOptions
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    mapToolbarEnabled = false
                )
            ) {
                // Marcadores dos Locais consoante a base de dados
                locais.forEach { local ->
                    Marker(
                        state = MarkerState(position = LatLng(local.latitude, local.longitude)),
                        title = local.nome,
                        icon = com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                            MapUtils.getMarkerIcon(local.categoria)
                        ),
                        onClick = {
                            selectedLocation = local // Abre o BottomSheet
                            false
                        }
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


// Botão flutuante para adicionar Local
        AddLocationFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {

                val centroMapa = cameraPositionState.position.target


                onNavigateToAddLocation(centroMapa.latitude, centroMapa.longitude)
            }
        )

        // Modal Bottom Sheet
        if (selectedLocation != null) {
            ModalBottomSheet(

                onDismissRequest = {
                    selectedLocation = null

                },
                sheetState = sheetState,
                containerColor = shett,
                contentColor = Color.White

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 48.dp)

                ) {
                    Text(
                        text = selectedLocation!!.categoria.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = Primary,

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = selectedLocation!!.nome,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = selectedLocation!!.descricao,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = "Horário")
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Horário: ${selectedLocation!!.horario}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Morada")
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = selectedLocation!!.morada,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

    }
}

// --- Componentes Auxiliares ---

@Composable
fun MapHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(shett),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "IPVCConecta",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
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
            .background(shett)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Pesquisar...", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Primary) },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(query)
                keyboardController?.hide()
            }),
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                // Texto que escreves
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                // O cursor que pisca
                cursorColor = Color.White,
                // Fundo da caixa (transparente ou ligeiramente mais claro para destacar)
                focusedContainerColor = Color.White.copy(alpha = 0.1f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                // Cor da borda
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
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
    }
}