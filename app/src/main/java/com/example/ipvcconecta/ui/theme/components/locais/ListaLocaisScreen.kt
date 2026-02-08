package com.example.ipvcconecta.ui.theme.components.locais

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ipvcconecta.ui.theme.Background
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.PrimaryDark
import com.example.ipvcconecta.ui.theme.shett

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaLocaisScreen(
    categoria: String,
    locais: List<LocalDetalhe>, // <--- NOVO: Recebe a lista viva (do Room/Firebase)
    onLocalClick: (LocalDetalhe) -> Unit,
    onBackClick: () -> Unit
) {
    // 1. Filtramos a lista viva pela categoria escolhida
    val locaisFiltrados = locais.filter { it.categoria == categoria }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = categoria) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = shett,
                    titleContentColor = Color.White,
                    navigationIconContentColor = shett
                )
            )
        }
    ) { paddingValues ->
        // 2. Se a lista estiver vazia (ex: acabaste de criar a categoria e ainda não tem locais)
        if (locaisFiltrados.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Ainda não existem locais nesta categoria.", color = Color.Gray)
            }
        } else {
            // 3. Lista Normal
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(locaisFiltrados) { local ->
                    LocalCard(local = local, onClick = { onLocalClick(local) })
                }
            }
        }
    }
}

// O LocalCard mantém-se igual (podes mantê-lo no fundo do ficheiro como tinhas)
@Composable
fun LocalCard(local: LocalDetalhe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = shett)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = local.nome,
                style = MaterialTheme.typography.titleMedium,
                color = Primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = local.morada,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}