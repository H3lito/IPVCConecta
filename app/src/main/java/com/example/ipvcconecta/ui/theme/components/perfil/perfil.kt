package com.example.ipvcconecta.ui.theme.components.perfil

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PerfilScreen(
    onFavoritosClick: () -> Unit = {},
    viewModel: PerfilViewModel = viewModel(),
    onLogoutClick: () -> Unit = {}
) {
    val nome by viewModel.nome.collectAsState()
    val email by viewModel.email.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PerfilHeader()

        Spacer(modifier = Modifier.height(24.dp))

        PerfilAvatar()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nome do Utilizador",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "email@ipvc.pt",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        PerfilOption(
            text = "Favoritos",
            icon = Icons.Default.Favorite,
            onClick = onFavoritosClick
        )

        PerfilOption(
            text = "Alterar foto",
            icon = Icons.Default.Edit,
            onClick = { }
        )

        PerfilOption(
            text = "Sair",
            icon = Icons.Default.ExitToApp,
            onClick ={
                viewModel.logout()
                onLogoutClick},
            isDanger = true
        )
    }
}

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
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun PerfilAvatar() {
    Box(
        modifier = Modifier
            .size(96.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
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
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDanger) Color.Red else MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isDanger) Color.Red else Color.Black
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
