package com.example.ipvcconecta.ui.theme.components.login

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun ForgotPasswordDialog(
    onDismiss: () -> Unit
) {
    var resetEmail by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Esqueceu-se da palavra-passe?") },
        text = {
            OutlinedTextField(
                value = resetEmail,
                onValueChange = { resetEmail = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                enabled = !isLoading,
                onClick = {
                    if (resetEmail.isBlank()) {
                        Toast.makeText(
                            context,
                            "Introduza o teu e-mail",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@TextButton
                    }

                    isLoading = true
                    Firebase.auth
                        .sendPasswordResetEmail(resetEmail)
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Email enviado. Verifique a sua caixa de correio.",
                                    Toast.LENGTH_LONG
                                ).show()
                                onDismiss()
                            } else {
                                Toast.makeText(
                                    context,
                                    task.exception?.message ?: "Erro",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            ) {
                Text("Enviar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}