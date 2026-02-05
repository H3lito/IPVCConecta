package com.example.ipvcconecta.ui.theme.components.locais

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ipvcconecta.ui.theme.PrimaryDark

@Composable
fun ListaLocaisScreen(
    categoria: String,
    onLocalClick: (LocalDetalhe) -> Unit = {}
) {
    // 1. Filtrar os dados reais do ficheiro LocaisData
    val locaisFiltrados = remember(categoria) {
        LocaisData.carregarLocaisIniciais().filter { local ->
            // Normalizamos para minúsculas para evitar erros (ex: "Escola" vs "escola")
            local.categoria.equals(categoria, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Cabeçalho Simples
        Box(
            modifier = Modifier.fillMaxWidth().height(56.dp).background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("IPVCConecta", style = MaterialTheme.typography.titleMedium, color = PrimaryDark)
        }

        // Título da Categoria
        Text(
            text = categoria,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
        )

        if (locaisFiltrados.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sem locais nesta categoria.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
            ) {
                items(locaisFiltrados) { local ->
                    LocalCard(local = local, onClick = { onLocalClick(local) })
                }
            }
        }
    }
}

@Composable
fun LocalCard(local: LocalDetalhe, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(local.nome, style = MaterialTheme.typography.titleMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(local.descricao, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, maxLines = 2)
        }
    }
}