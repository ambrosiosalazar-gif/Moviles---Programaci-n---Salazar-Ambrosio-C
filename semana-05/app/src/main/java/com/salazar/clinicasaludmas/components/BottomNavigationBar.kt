package com.salazar.clinicasaludmas.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Mis citas") },
            label = { Text("Mis citas") },
            selected = currentRoute == Screen.MisCitas.route,
            onClick = { navController.navigate(Screen.MisCitas.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Resultados") },
            label = { Text("Resultados") },
            selected = currentRoute == Screen.Resultados.route,
            onClick = { navController.navigate(Screen.Resultados.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentRoute == Screen.Perfil.route,
            onClick = { navController.navigate(Screen.Perfil.route) }
        )
    }
}