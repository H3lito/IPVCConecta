package com.example.ipvcconecta.ui.theme.components.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.example.ipvcconecta.ui.theme.shett

@Composable
fun FavoritosScreen(

    viewModel: FavoritosViewModel = viewModel(),
    onLocalClick: (LocalDetalhe) -> Unit = {} // Recebe LocalDetalhe
) {
    val favoritos by viewModel.favoritos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FavoritosHeader()

        if (favoritos.isEmpty()) {
            FavoritosEmptyState()
        } else {

            FavoritosList(
                favoritos = favoritos,
                onLocalClick = onLocalClick
            )
        }
    }
}

@Composable
fun FavoritosHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(shett)
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Os Meus Favoritos ❤️",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun FavoritosEmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ainda não adicionaste favoritos",
            style = MaterialTheme.typography.bodyMedium,
            color = Primary
        )
    }
}

@Composable
fun FavoritosList(
    favoritos: List<LocalDetalhe>,
    onLocalClick: (LocalDetalhe) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(favoritos) { local ->
            FavoritoCard(local = local) {
                onLocalClick(local)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavScreenPreview() {
    MaterialTheme {
        FavoritosScreen()
    }
}