package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.components.BottomNavigationBar
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMedicoScreen(navController: NavController) {
    val citas = MedicosRepository.citasDelUsuario()
    val completadas = citas.filter { it.estado == "Completada" || it.estado == "Confirmada" }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Historial médico / Resultados") })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = Screen.Resultados.route)
        }
    ) { padding ->
        if (completadas.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Todavía no tienes atenciones registradas")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(all = 16.dp)) {
                items(completadas) { cita ->
                    ListItem(
                        headlineContent = { Text(cita.nombreMedico) },
                        supportingContent = { Text("${cita.especialidadNombre} · ${cita.fecha}") }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}