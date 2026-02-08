package com.example.ipvcconecta.ui.theme.components.guia

import androidx.benchmark.traceprocessor.Row
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.shett

// 1. Dados da Pergunta
data class GuiaItem(
    val titulo: String,
    val resposta: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuiaSouNovoScreen(
    onBackClick: () -> Unit
) {
    // Lista de Perguntas (Podes adicionar mais aqui)
    val listaPerguntas = listOf(
        GuiaItem(
            "Como chegar à Universidade?",
            "A Universidade pode ser alcançada através das linhas de autocarro urbano ou a pé, dependendo da localização do estudante. Recomenda-se a utilização de transportes públicos ou as bicicletas partilhadas."
        ),
        GuiaItem(
            "Onde tratar dos documentos?",
            "Os serviços académicos funcionam no edifício central. Podes tratar de matrículas, passes e declarações no R/C, aberto das 09:00 às 16:00."
        ),
        GuiaItem(
            "Transportes da Cidade?",
            "Viana tem uma rede de autocarros urbanos e funicular. Podes comprar o passe mensal na central de camionagem ou usar a app VianaTransportes."
        ),
        GuiaItem(
            "Onde posso comer?",
            "Existem cantinas em cada escola do IPVC com preços sociais. Também tens o bar dos alunos e vários restaurantes parceiros na cidade."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = shett)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabeçalho
            Text(
                text = "IPVCConecta",
                style = MaterialTheme.typography.titleLarge,
                color = Primary,
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = Icons.Default.PeopleAlt, // Ícone de estudante/escola
                contentDescription = null,
                modifier = Modifier.size(40.dp).padding(top = 8.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bem-vindo(a) a Viana do Castelo!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Aqui encontras informações essenciais para começares a tua vida académica na cidade.",
                fontSize = 14.sp,
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Lista de Cartões Expansíveis
            listaPerguntas.forEach { item ->
                ExpandableCard(title = item.titulo, description = item.resposta)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// --- O COMPONENTE MÁGICO (ACCORDION) ---
@Composable
fun ExpandableCard(
    title: String,
    description: String
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize( // Animação suave ao abrir/fechar
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = shett) // Azul Escuro
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Linha do Título e Seta
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White, // Ícone preto como na imagem
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White, // Texto preto como na imagem
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expandir",
                    modifier = Modifier
                        .rotate(rotationState), // Roda a seta
                    tint = Color.Black
                )
            }

            // Conteúdo escondido
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                // Linha divisória fina (opcional)
                HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.White, // Texto branco para ler no fundo azul
                    textAlign = TextAlign.Justify,
                    lineHeight = 20.sp
                )
            }
        }
    }
}