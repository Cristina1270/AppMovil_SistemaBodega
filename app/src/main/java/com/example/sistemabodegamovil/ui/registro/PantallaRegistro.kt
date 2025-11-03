package com.example.sistemabodegamovil.ui.registro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistemabodegamovil.CustomTextField
import com.example.sistemabodegamovil.R
import com.example.sistemabodegamovil.data.model.UsuarioDTO
import com.example.sistemabodegamovil.data.network.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun PantallaRegistro(navController: NavController) {
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_galaxy),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))
                Text(
                    "Crear cuenta",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color(0xFF1A237E),
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(24.dp))

                CustomTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
                Spacer(Modifier.height(12.dp))
                CustomTextField(value = apellidoPaterno, onValueChange = { apellidoPaterno = it }, label = "Apellido paterno")
                Spacer(Modifier.height(12.dp))
                CustomTextField(value = apellidoMaterno, onValueChange = { apellidoMaterno = it }, label = "Apellido materno")
                Spacer(Modifier.height(12.dp))
                CustomTextField(value = rut, onValueChange = { rut = it }, label = "RUT")
                Spacer(Modifier.height(12.dp))
                CustomTextField(value = correo, onValueChange = { correo = it }, label = "Correo electrónico")
                Spacer(Modifier.height(12.dp))
                CustomTextField(value = contrasenia, onValueChange = { contrasenia = it }, label = "Contraseña", isPassword = true)

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            cargando = true
                            mensaje = null
                            try {
                                val dto = UsuarioDTO(
                                    correo = correo,
                                    contrasenia = contrasenia,
                                    rut = rut,
                                    nombre = nombre,
                                    apellidoPaterno = apellidoPaterno,
                                    apellidoMaterno = apellidoMaterno
                                )
                                val resp = RetrofitInstance.api.crearUsuario(dto)
                                mensaje = resp.mensaje
                                if (resp.estado == "OK") {
                                    navController.navigate("login")
                                }
                            } catch (e: Exception) {
                                mensaje = "Error: ${e.message}"
                            } finally {
                                cargando = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A237E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !cargando
                ) {
                    Text(if (cargando) "Registrando..." else "Registrar")
                }

                Spacer(Modifier.height(16.dp))
                mensaje?.let {
                    Text(it, color = if (it.contains("Error", true)) Color.Red else Color(0xFF1A237E))
                }

                Spacer(Modifier.height(16.dp))
                Row {
                    Text("¿Ya tienes una cuenta? ")
                    Text(
                        "Inicia sesión",
                        color = Color(0xFF1A237E),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.navigate("login") }
                    )
                }
            }
        }
    }
}