package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.components.BottomNavigationBar
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val usuario = MedicosRepository.usuarioActual

    Scaffold(
        topBar = { TopAppBar(title = { Text("¡Hola, ${usuario?.nombre ?: "Paciente"}!") }) },
        bottomBar = { BottomNavigationBar(navController = navController, currentRoute = Screen.Home.route) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate(Screen.Especialidades.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar cita médica")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Especialidades Destacadas", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(MedicosRepository.especialidadesDestacadas()) { espec ->
                    Card(
                        onClick = { navController.navigate(Screen.Medicos.createRoute(espec.id)) },
                        modifier = Modifier.size(140.dp, 90.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                            Text(espec.nombre)
                        }
                    }
                }
            }
        }
    }
}