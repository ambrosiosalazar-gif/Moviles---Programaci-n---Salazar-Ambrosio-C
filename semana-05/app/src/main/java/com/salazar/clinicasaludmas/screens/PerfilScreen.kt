package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.components.BottomNavigationBar
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.navigation.Screen

@Composable
fun PerfilScreen(navController: NavController) {
    val usuario = MedicosRepository.usuarioActual

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, currentRoute = Screen.Perfil.route) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)
        ) {
            Text("Mi Perfil", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            Text("Nombre: ${usuario?.nombre ?: "Sin datos"}")
            Text("DNI: ${usuario?.dni ?: "Sin datos"}")
            Text("Correo: ${usuario?.correo ?: "Sin datos"}")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    MedicosRepository.cerrarSesion()
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(0)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}