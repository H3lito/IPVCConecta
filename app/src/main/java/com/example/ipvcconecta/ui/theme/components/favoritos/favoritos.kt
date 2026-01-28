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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipvcconecta.ui.theme.components.mapa.MapScreen
import com.example.ipvcconecta.ui.theme.data.model.LocalFavorito

@Composable
fun FavoritosScreen(
    //onLocalClick: (LocalFavorito) -> Unit = {}
    viewModel: FavoritosViewModel = viewModel(),
    onLocalClick: (LocalFavorito) -> Unit = {}
) {
    val favoritos by viewModel.favoritos.collectAsState()
    if(favoritos.isEmpty()) {
    FavoritosEmptyState()
    } else{
        FavoritosList(
            favoritos = favoritos,
            onLocalClick = onLocalClick
        )
    }
    // 🔹 Por agora: lista vazia (simula utilizador novo)
    //val favoritos = remember {
        //emptyList<LocalFavorito>()
        // quando quiseres testar com dados:
        // listOf(LocalFavorito("Cantina IPVC", "Alimentação"))
    //}

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
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Os Meus Favoritos ❤️",
            style = MaterialTheme.typography.titleMedium
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
            color = Color.Gray
        )
    }
}
@Composable
fun FavoritosList(
    favoritos: List<LocalFavorito>,
    onLocalClick: (LocalFavorito) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(favoritos) { local ->
            FavoritoCard(local) {
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

