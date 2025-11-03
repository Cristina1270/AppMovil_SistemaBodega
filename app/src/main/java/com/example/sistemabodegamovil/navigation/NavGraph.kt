package com.example.sistemabodegamovil.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sistemabodegamovil.ui.login.PantallaLogin
import com.example.sistemabodegamovil.ui.registro.PantallaRegistro
import com.example.sistemabodegamovil.ui.welcome.PantallaWelcome
import com.example.sistemabodegamovil.ui.home.PantallaHome

@Composable
fun MyApp() {
    val navController = rememberNavController()

    androidx.compose.material3.Text("Cargando NavHost...")

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome")     { com.example.sistemabodegamovil.ui.welcome.PantallaWelcome(navController) }
        composable("registro")    { com.example.sistemabodegamovil.ui.registro.PantallaRegistro(navController) }
        composable("login")       { com.example.sistemabodegamovil.ui.login.PantallaLogin(navController) }
        composable("home")        { com.example.sistemabodegamovil.ui.home.PantallaHome(navController) }
        composable("productos")   { com.example.sistemabodegamovil.ui.productos.PantallaProductos(navController) }

    }
}
