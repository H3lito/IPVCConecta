package com.example.ipvcconecta.ui.theme.components.mapa
import android.Manifest
import android.content.pm.PackageManager
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    mapViewModel: MapViewModel = viewModel(),
    onNavigateToAddLocation: () -> Unit
) {
    val context = LocalContext.current

    // Permissões
    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mapViewModel.getDeviceLocation()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            mapViewModel.getDeviceLocation()
        }
    }

    // Observar estados do ViewModel
    val cameraLocation by mapViewModel.cameraLocation.collectAsState()
    val searchResultLocation by mapViewModel.searchResultLocation.collectAsState()
    val searchResultTitle by mapViewModel.searchResultTitle.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        // Mapa e Marcadores
        MapContent(
            hasLocationPermission = hasLocationPermission,
            cameraLocation = cameraLocation,
            searchResultLocation = searchResultLocation, // Passar o local pesquisado
            searchResultTitle = searchResultTitle
        )

        // Elementos da UI sobrepostos
        Column(modifier = Modifier.fillMaxSize()) {
            MapHeader()

            SearchBar(
                onSearch = { query ->
                    mapViewModel.searchLocation(query, context)
                }
            )
        }

        // --- BOTÃO RECENTRAR (Canto Superior Direito) ---
        // Colocamos padding(top = 130.dp) para ficar abaixo da barra de pesquisa
        if (hasLocationPermission) {
            SmallFloatingActionButton(
                onClick = { mapViewModel.getDeviceLocation() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 130.dp, end = 16.dp),
                containerColor = Color.White,
                contentColor = PrimaryDark
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Minha Localização"
                )
            }
        }

        // Botão Adicionar (Canto Inferior Direito)
        AddLocationFAB(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { onNavigateToAddLocation() }
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
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
            placeholder = { Text("Pesquisar locais...", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Pesquisar", tint = Color.Gray)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp),
            // Configuração do Teclado para Pesquisa
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
fun MapContent(
    hasLocationPermission: Boolean,
    cameraLocation: LatLng?,
    searchResultLocation: LatLng?, // Coordenada da pesquisa
    searchResultTitle: String
) {
    val defaultLocation = LatLng(41.6932, -8.8329)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }

    // Move a câmara quando o ViewModel pede
    LaunchedEffect(cameraLocation) {
        cameraLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 16f)
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            // ISTO CRIA A BOLA AZUL DA LOCALIZAÇÃO ATUAL
            isMyLocationEnabled = hasLocationPermission
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false, // Desligamos o nativo para usar o nosso botão
            mapToolbarEnabled = false
        )
    ) {
        // --- DESENHAR MARCADOR DA PESQUISA ---
        searchResultLocation?.let { location ->
            Marker(
                state = MarkerState(position = location),
                title = searchResultTitle,
                snippet = "Resultado da pesquisa"
            )
        }
    }
}

// ... Header e FAB mantêm-se iguais, apenas garante que o FAB está a ser chamado no MapScreen ...
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
fun AddLocationFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(16.dp),
        containerColor = Primary
    ) {
        Icon(Icons.Default.Add, contentDescription = "Adicionar local", tint = Color.White)
    }
}
