package com.example.sistemabodegamovil.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sistemabodegamovil.feature.productos.ProductoViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaHome(
    navController: NavController,
    vm: ProductoViewModel = viewModel()
) {
    val productos = vm.productos.collectAsState().value //LEE LOS PRODUCTOS DE ROOM

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val fondoGradiente = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1E2E52),
            Color(0xFF243F74),
            Color(0xFF264886)
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(Modifier.padding(16.dp)) {
                    Text("Acciones Rápidas", color = Color.White)
                    Divider(color = Color.White.copy(alpha = .3f))
                    Spacer(Modifier.height(8.dp))

                    DrawerItem("Ver Productos") { navController.navigate("productos") }
                    DrawerItem("Ver Movimientos") { navController.navigate("movimientos") }
                    DrawerItem("Despacho") { navController.navigate("despacho") }
                    DrawerItem("Recepción") { navController.navigate("recepcion") }
                    DrawerItem("Consulta") { navController.navigate("consulta") }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoGradiente)
                .padding(24.dp)
        ) {

            //TOP BAR
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        scope.launch { drawerState.open() }
                    }
                )

                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvendido/a",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = "¡Tu gestión comienza aquí!",
                color = Color(0xFFBBDEFB),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            //PRODUCTOS
            Text("Productos", color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                            .height(130.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3F51B5)),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Column {
                                Text(
                                    text = producto.nombre,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Código: ${producto.codigo}",
                                    color = Color.White.copy(alpha = .7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Ver todos los productos") },
                    leadingContent = { Icon(Icons.Default.List, contentDescription = null) },
                    trailingContent = { Icon(Icons.Default.KeyboardArrowRight, contentDescription = null) },
                    modifier = Modifier.clickable { navController.navigate("productos") }
                )
            }
        }
    }
}



@Composable
fun DrawerItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}
@Composable
fun KPIItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF7986CB)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(24.dp))
            Text(text = label, color = Color.White, fontSize = 12.sp)
            Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}


@Composable
fun ProductoRecienteCard(nombre: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = nombre, fontWeight = FontWeight.SemiBold)
            Icon(Icons.Default.Inventory, contentDescription = null)
        }
    }
}