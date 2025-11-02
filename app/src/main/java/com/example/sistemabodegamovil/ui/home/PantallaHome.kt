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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun PantallaHome(
    navController: NavController,
    userName: String = "Cristina",
    productos: List<String> = listOf("P1", "P2", "P3", "P4", "P5")
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //Fondo
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
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF001D3D), // Azul muy oscuro
                                    Color(0xFF003566), // Azul medio oscuro
                                    Color(0xFF0062A3)  // Azul corporativo
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Acciones Rápidas",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Divider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        DrawerItem("Ver Productos") { navController.navigate("productos") }
                        DrawerItem("Ver Movimientos") { navController.navigate("movimientos") }
                        DrawerItem("Despacho") { navController.navigate("despacho") }
                        DrawerItem("Recepción") { navController.navigate("recepcion") }
                        DrawerItem("Consulta") { navController.navigate("consulta") }
                    }
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
            // 🔹 Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        scope.launch { drawerState.open() }
                    }
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Bienvenida
            Text(
                text = "Hola, $userName 👋",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = "Que tengas un buen día ✨",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFBBDEFB) // Azul claro pastel
            )

            //KPIs
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                KPIItem(
                    icon = Icons.Default.Inventory2,
                    label = "Stock",
                    value = "3,200",
                    modifier = Modifier.weight(1f)
                )
                KPIItem(
                    icon = Icons.Default.ReceiptLong,
                    label = "Movimientos",
                    value = "8",
                    modifier = Modifier.weight(1f)
                )
                KPIItem(
                    icon = Icons.Default.ShoppingCart,
                    label = "Productos",
                    value = "154",
                    modifier = Modifier.weight(1f)
                )
            }

            //Productos
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
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
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                            Column {
                                Text(
                                    text = producto,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Octubre 2025",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            //Productos recientes
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Productos Recientes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            productos.take(3).forEach { producto ->
                ProductoRecienteCard(nombre = producto)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Movimientos
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Movimientos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Ver todos los movimientos") },
                    leadingContent = { Icon(Icons.Default.List, contentDescription = null) },
                    trailingContent = { Icon(Icons.Default.KeyboardArrowRight, contentDescription = null) },
                    modifier = Modifier.clickable { navController.navigate("movimientos") }
                )
            }
        }
    }
}



@Composable
fun DrawerItem(
    text: String,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
        )
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