package com.example.ipvcconecta.ui.theme.components.guia
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
    // Lista de Perguntas
    val listaPerguntas = listOf(
        GuiaItem(
            "Como chegar à ESTG - IPVC?",
            "A ESTG-IPVC localiza-se em Viana do Castelo, na Avenida do Atlântico, junto à Praia Norte. O campus é acessível de carro, através da A28 (saída Viana do Castelo Sul), de comboio até à Estação de Viana do Castelo com ligação por autocarro ou táxi, bem como através dos transportes urbanos da cidade. Para quem reside nas proximidades, é também possível chegar a pé ou de bicicleta."
        ),
        GuiaItem(
            "Onde tratar dos documentos?",
            "Os Serviços Académicos da ESTG-IPVC encontram-se localizados na própria escola. Neste espaço é possível tratar da matrícula, pedidos de declarações (incluindo para emissão de passe), certificados e pagamento de propinas. Os horários de funcionamento são variáveis e estão disponíveis no site oficial do IPVC."
        ),
        GuiaItem(
            "Transportes da Cidade?",
            "Viana do Castelo dispõe de uma rede de transportes urbanos que liga o centro da cidade, a estação de comboios e as principais zonas residenciais à área da ESTG-IPVC. Existem ainda serviços de táxi e transporte por aplicação que facilitam as deslocações dentro da cidade."
        ),
        GuiaItem(
            "Onde posso comer?",
            "Cada escola do IPVC dispõe de cantinas com refeições sociais a preços acessíveis. Para além disso, existem várias tascas e restaurantes espalhados pela cidade, assim como um shopping com uma área de restauração que oferece diversas opções de refeição."
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
            .animateContentSize( // Animação ao abrir/fechar
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
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expandir",
                    modifier = Modifier
                        .rotate(rotationState),
                    tint = Color.Black
                )
            }

            // Informação escondida
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    lineHeight = 20.sp
                )
            }
        }
    }
}