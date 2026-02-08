package com.example.ipvcconecta.ui.theme.components.perfil

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.shett


@Composable
fun PerfilScreen(
    onFavoritosClick: () -> Unit = {},
    viewModel: PerfilViewModel = viewModel(),
    onGuiaClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val nome by viewModel.nome.collectAsState()
    val email by viewModel.email.collectAsState()
    val fotoUri by viewModel.fotoUri.collectAsState()

    // 1. LER O ESTADO DE LOADING
    val isLoading by viewModel.isLoading.collectAsState()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.atualizarFoto(uri)
            }
        }
    )

    // 2. USAR UM BOX COMO RAIZ (Para empilhar o loading por cima)
    Box(modifier = Modifier.fillMaxSize()) {

        // --- CONTEÚDO DO ECRÃ (Fica por baixo) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PerfilHeader()

            Spacer(modifier = Modifier.height(24.dp))

            PerfilAvatar(fotoUri = fotoUri)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = nome,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = shett
            )

            Spacer(modifier = Modifier.height(32.dp))

            PerfilOption(
                text = "Sou Novo",
                icon = Icons.Default.School,
                onClick = onGuiaClick,

            )


            PerfilOption(
                text = "Favoritos",
                icon = Icons.Default.Favorite,
                onClick = onFavoritosClick
            )

            PerfilOption(
                text = "Alterar foto",
                icon = Icons.Default.Edit,
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )

            PerfilOption(
                text = "Sair",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                onClick = {
                    viewModel.logout()
                    onLogoutClick()
                },
                isDanger = true
            )
        }

        // --- INDICADOR DE CARREGAMENTO (Fica por cima) ---
        if (isLoading) {
            // Fundo semi-transparente para bloquear cliques enquanto carrega
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(enabled = false) {} // Bloqueia cliques
            )

            // O Spinner propriamente dito
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// --- (As funções PerfilHeader, PerfilAvatar e PerfilOption mantêm-se iguais abaixo) ---
@Composable
fun PerfilHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Perfil",
            style = MaterialTheme.typography.titleLarge,
            color= Primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PerfilAvatar(fotoUri: Uri?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Color(0xFFE0E0E0))
    ) {
        if (fotoUri != null) {
            AsyncImage(
                model = fotoUri,
                contentDescription = "Foto de Perfil",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun PerfilOption(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isDanger: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                // Se for 'Perigo' (Sair) fica Branco, senão fica AZUL PETRÓLEO
                containerColor =  shett
                )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDanger) Color.Red else Color.White
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isDanger) Color.Red else Color.White
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    MaterialTheme {
        PerfilScreen()
    }
}
