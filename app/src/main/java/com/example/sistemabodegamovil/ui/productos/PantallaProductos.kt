package com.example.sistemabodegamovil.ui.productos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sistemabodegamovil.R
import com.example.sistemabodegamovil.data.local.ProductoEntity
import com.example.sistemabodegamovil.feature.productos.ProductoViewModel

@Composable
fun PantallaProductos(
    navController: NavController,
    vm: ProductoViewModel = viewModel()
) {
    val productos = vm.productos.collectAsState().value

    // Estados locales para los campos
    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_galaxy),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Botón volver
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        // Panel blanco inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Agregar Producto",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF1A237E)
                )

                // Campos
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = precioText,
                    onValueChange = { precioText = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        val precio = precioText.toDoubleOrNull()
                        if (nombre.isNotBlank() && codigo.isNotBlank()) {
                            vm.agregarProducto(codigo, nombre, precio)
                            // Limpiar campos
                            nombre = ""
                            codigo = ""
                            precioText = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
                ) {
                    Text("Agregar")
                }

                Divider()

                Text(
                    text = "Lista de Productos",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1A237E)
                )

                if (productos.isEmpty()) {
                    Text("No hay productos aún.", color = Color.Gray)
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(productos) { producto ->
                            ProductoCard(producto)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCard(producto: ProductoEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6A5ACD)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = producto.nombre,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Código: ${producto.codigo}",
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = "Precio: $${producto.precio ?: 0.0}",
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}
