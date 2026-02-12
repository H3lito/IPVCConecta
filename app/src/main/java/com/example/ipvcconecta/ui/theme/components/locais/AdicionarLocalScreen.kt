package com.example.ipvcconecta.ui.theme.components.locais

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipvcconecta.ui.theme.Primary
import com.example.ipvcconecta.ui.theme.shett

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarLocalScreen(
    onBackClick: () -> Unit,
    viewModel: AddLocalViewModel = viewModel()
) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var morada by remember { mutableStateOf("") }
    var horario by remember{mutableStateOf("")}

    val isLoading by viewModel.isLoading.collectAsState()
    val success by viewModel.uploadSuccess.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    // Se o envio for bem sucedido, volta para trás automaticamente
    LaunchedEffect(success) {
        if (success) {
            onBackClick()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(
                    text = "IPVCConecta",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint= Color.White)
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
                .background(Color.White)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. TÍTULO
            Text(
                text = "Sugerir Local",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                value = nome,
                onValueChange = { nome = it },
                label = "Nome do Local:"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = "Categoria (Ex: Alimentação):"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = "Descrição:",
                singleLine = false,
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = morada,
                onValueChange = { morada = it },
                label = "Morada/Localização:",
                singleLine = false,
                minLines = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = horario,
                onValueChange = {horario = it},
                label = "Horário Funcionamento:",
            )

            Spacer(modifier = Modifier.height(40.dp))


            if (isLoading) {
                CircularProgressIndicator(color = Primary)
            } else {
                Button(

                    onClick = { viewModel.submeterLocal(nome, categoria, descricao, morada, horario) },
                    colors = ButtonDefaults.buttonColors(containerColor = shett),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .width(220.dp)
                        .height(50.dp)

                ) {
                    Text("Submeter Sugestão", fontSize = 16.sp, color = Color.White)
                }
            }

            // Mensagem de Erro (se houver)
            if (error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = error!!, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. AVISO IMPORTANTE DE UX
            Text(
                text = "As sugestões serão analisadas pela equipa\nantes de serem publicadas no mapa.",
                fontSize = 12.sp,
                color = shett,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Componente Auxiliar para as caixas de texto
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier .fillMaxSize() .padding(bottom = 4.dp),
            color = Primary

        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            singleLine = singleLine,
            minLines = minLines,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = shett,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}