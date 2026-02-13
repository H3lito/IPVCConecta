package com.example.ipvcconecta.ui.theme.components.explorar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.shett

@Composable
fun ExplorarScreen(
    viewModel: ExplorarViewModel = viewModel(),
    onCategoryClick: (String) -> Unit = {}) {
    val categorias by viewModel.categorias.collectAsState()
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            MapHeader()

            ExplorarContent(
                categorias = categorias,
                onCategoryClick = onCategoryClick
            )

        }
    }
}

@Composable
fun MapHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(shett),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "IPVCConecta",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable
fun ExplorarContent(
    categorias: List<String>,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Explorar Serviços",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp),
            color= Primary,
            fontWeight = FontWeight.Bold
        )

        categorias.forEach { categoria ->
            ExplorarItem(
                title = categoria,
                icon = categoriaToIcon(categoria),
                onClick = onCategoryClick
            )
        }
    }
}
@Composable
fun categoriaToIcon(categoria: String): ImageVector {
    return when (categoria) {
        "Escolas" -> Icons.Default.School
        "Transportes" -> Icons.Default.DirectionsBus
        "Alimentação" -> Icons.Default.Restaurant
        "Serviços" -> Icons.Filled.DesignServices
        "Biblioteca" -> Icons.Default.LocalLibrary
        else -> Icons.Default.Place
    }
}

@Composable
fun ExplorarItem(
    title: String,
    icon: ImageVector,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(title)},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = shett
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White

            )
        }
    }
}

