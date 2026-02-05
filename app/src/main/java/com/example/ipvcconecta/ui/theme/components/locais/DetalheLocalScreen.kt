package com.example.ipvcconecta.ui.theme.components.locais

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ipvcconecta.ui.theme.PrimaryDark
import com.example.ipvcconecta.ui.theme.Surface

data class LocalDetalhe(
    val nome: String,
    val categoria: String,
    val descricao: String,
    val morada: String,
    val horario: String,
    val latitude: Double,
    val longitude: Double
)
@Composable
fun DetalheLocalScreen(
    local: LocalDetalhe,
    isFavorito: Boolean = false, // <--- NOVO PARÂMETRO
    onBackClick: () -> Unit = {},
    onFavoritoClick: () -> Unit = {},
    onVerMapaClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = local.nome,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoriaChip(local.categoria)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Descrição",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = local.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoItem("Morada", local.morada)
            InfoItem("Horário", local.horario)

            Spacer(modifier = Modifier.height(24.dp))

            AcoesLocal(
                isFavorito = isFavorito, // Passar para baixo
                onFavoritoClick = onFavoritoClick,
                onVerMapaClick = onVerMapaClick
            )
        }
    }
}
@Composable
fun DetalheHeader(
    local: LocalDetalhe,
    onBackClick: () -> Unit = {},
    onFavoritoClick: () -> Unit = {},
    onVerMapaClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "IPVCConecta",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryDark
        )
    }
}

@Composable
fun CategoriaChip(categoria: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE0E0E0)
    ) {
        Text(
            text = categoria,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
@Composable
fun InfoItem(
    titulo: String,
    valor: String
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
// Atualizar também a função AcoesLocal para receber o booleano e trocar o ícone
@Composable
fun AcoesLocal(
    isFavorito: Boolean,
    onFavoritoClick: () -> Unit,
    onVerMapaClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onFavoritoClick,
            modifier = Modifier.weight(1f),
            // Opcional: Mudar cor se for favorito
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFavorito) Color(0xFFE91E63) else MaterialTheme.colorScheme.primary
            )
        ) {
            // TROCAR O ÍCONE AQUI
            Icon(
                imageVector = if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isFavorito) "Remover" else "Favorito")
        }

        OutlinedButton(
            onClick = onVerMapaClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Place, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ver no mapa")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DetalheLocalPreview() {
    MaterialTheme {
        DetalheLocalScreen(
            local = LocalDetalhe(
                nome = "Cantina IPVC",
                categoria = "Alimentação",
                descricao = "Espaço de refeições acessível para estudantes.",
                morada = "Av. do Atlântico, Viana do Castelo",
                horario = "Seg–Sex: 12h–14h",
                latitude = 41.6932,
                longitude =-8.8329
            )
        )
    }
}
