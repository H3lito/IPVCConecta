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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.shett

data class LocalDetalhe(
    val id: String = "",
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
    isFavorito: Boolean = false,
    onBackClick: () -> Unit = {},
    onFavoritoClick: () -> Unit = {},
    onVerMapaClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        DetalheHeader(
            onBackClick = onBackClick
        )
        // -----------------------------------

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = local.nome,
                style = MaterialTheme.typography.titleLarge,
                color = Primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoriaChip(local.categoria)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Descrição",
                style = MaterialTheme.typography.titleMedium,
                color = Primary
            )

            Text(
                text = local.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoItem("Morada", local.morada)
            InfoItem("Horário", local.horario)

            Spacer(modifier = Modifier.height(24.dp))

            AcoesLocal(
                isFavorito = isFavorito,
                onFavoritoClick = onFavoritoClick,
                onVerMapaClick = onVerMapaClick
            )
        }
    }
}


@Composable
fun DetalheHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(shett)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "IPVCConecta",
            style = MaterialTheme.typography.titleMedium,
            color =Color.White ,
            fontWeight = FontWeight.Bold

        )
    }
}
@Composable
fun CategoriaChip(categoria: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = shett
    ) {
        Text(
            text = categoria,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
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
            color = Primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
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
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFavorito) Color.Red else MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isFavorito) "Remover" else "Favorito")
        }

        OutlinedButton(
            onClick = onVerMapaClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = shett
            ),

        ) {
            Icon(Icons.Default.Place, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ver no mapa")
        }
    }
}
