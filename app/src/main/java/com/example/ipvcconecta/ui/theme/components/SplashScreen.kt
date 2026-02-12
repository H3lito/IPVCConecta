package com.example.ipvcconecta.ui.theme.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ipvcconecta.R
import com.example.ipvcconecta.ui.theme.LoginRoute
import com.example.ipvcconecta.ui.theme.shett
import kotlinx.coroutines.delay

// Ecrã de Abertura com animação
@Composable
fun SplashScreen(navController: NavController) {

    // Gestão de estado de animação
    val scale = remember { Animatable(0f) }

    // O LaunchedEffect permite executar operações assíncronas
    LaunchedEffect(key1 = true) {
        // Animação de Interpolação
        scale.animateTo(
            targetValue = 1f, // Cresce até o tamanho original
            animationSpec = tween(
                durationMillis = 1000,
                easing = { OvershootInterpolator(2f).getInterpolation(it) }
            )
        )

        // 2. Espera um pouco e não bloqueia a Main Thread.
        delay(1500L)

        // 3. Navegação e Limpeza da Backstack.
        navController.navigate(LoginRoute) {
            // Remove o ecrã splash da pilha
        popUpTo("splash") { inclusive = true }
        }
    }

    // O Layout do Ecrã
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(shett) // Fundo Azul Escuro
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.ipvcconecta),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale.value)
                    .clip(CircleShape)
            )




            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "IPVCConecta",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.scale(scale.value)
            )
        }
    }
}