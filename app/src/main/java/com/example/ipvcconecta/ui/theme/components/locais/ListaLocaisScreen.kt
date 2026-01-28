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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ipvcconecta.ui.theme.PrimaryDark

data class LocalItem(
    val nome: String,
    val descricao: String
)

@Composable
fun ListaLocaisScreen(
    categoria: String,
    onLocalClick: (LocalItem) -> Unit = {}
) {
    // Dados mock (mais tarde vêm do Firebase)
    val locaisMock = listOf(
        LocalItem("Cantina IPVC", "Refeições a preços sociais"),
        LocalItem("Café Académico", "Espaço de convívio para estudantes"),
        LocalItem("Restaurante Central", "Alimentação variada")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ListaLocaisHeader()
        ListaLocaisTitle(categoria)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(locaisMock) { local ->
                LocalCard(
                    local = local,
                    onClick = { onLocalClick(local) }
                )
            }
        }
    }
}

@Composable
fun ListaLocaisHeader() {
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
fun ListaLocaisTitle(categoria: String) {
    Text(
        text = categoria,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp),
        color = Color.Black
    )
}

@Composable
fun LocalCard(
    local: LocalItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = local.nome,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = local.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaLocaisPreview() {
    MaterialTheme {
        ListaLocaisScreen(categoria = "Alimentação")
    }
}
