package com.example.sistemabodegamovil.ui.login

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sistemabodegamovil.CustomTextField
import com.example.sistemabodegamovil.R
import com.example.sistemabodegamovil.feature.auth.AuthViewModel

@Composable
fun PantallaLogin(navController: NavController) {
    val vm: AuthViewModel = viewModel()
    val ui = vm.ui.collectAsStateWithLifecycle().value

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var recordar by rememberSaveable { mutableStateOf(false) }
    var intentado by rememberSaveable { mutableStateOf(false) }

    val emailValido = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    val passValida = password.length >= 6
    val formularioValido = emailValido && passValida

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_galaxy),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Botón volver
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        // Tarjeta blanca inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter)
                .background(color = Color.White, shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Sign In",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1A237E)
                )

                // Campo Email
                CustomTextField(value = email, onValueChange = { email = it }, label = "Email")
                if (intentado && !emailValido) {
                    Text("Email inválido", color = Color.Red, style = MaterialTheme.typography.labelSmall)
                }

                // Campo Password
                CustomTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
                if (intentado && !passValida) {
                    Text("La contraseña debe tener al menos 6 caracteres", color = Color.Red, style = MaterialTheme.typography.labelSmall)
                }

                // Recordar y recuperar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = recordar, onCheckedChange = { recordar = it })
                        Text("Remember")
                    }
                    Text(
                        "Forgot your password?",
                        color = Color(0xFF1A237E),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.clickable { }
                    )
                }

                // Botón principal
                Button(
                    onClick = {
                        intentado = true
                        if (formularioValido && !ui.loading) {
                            vm.login(email.trim(), password)
                        }
                    },
                    enabled = formularioValido && !ui.loading,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A237E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (ui.loading) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Sign In")
                    }
                }

                // Error de backend
                if (ui.error != null) {
                    Text(ui.error ?: "", color = Color.Red, style = MaterialTheme.typography.labelSmall)
                }

                // Registro
                Row {
                    Text("Don't have an account? ")
                    Text(
                        "Sign up",
                        color = Color(0xFF1A237E),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { navController.navigate("registro") }
                    )
                }
            }
        }
    }

    // Navegar cuando el backend confirma éxito
    LaunchedEffect(ui.success) {
        if (ui.success) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
}
