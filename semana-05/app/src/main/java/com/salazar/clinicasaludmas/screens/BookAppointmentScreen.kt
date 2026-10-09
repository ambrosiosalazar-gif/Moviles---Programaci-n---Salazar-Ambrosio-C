package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    navController: NavController,
    medicoId: String
) {
    val medico = MedicosRepository.obtenerMedico(medicoId)
    val fechas = listOf("2026-10-12", "2026-10-13", "2026-10-14")

    var fechaSeleccionada by remember { mutableStateOf(fechas.first()) }
    val horariosDisponibles = MedicosRepository.horariosDisponibles(medicoId, fechaSeleccionada)
    var horaSeleccionada by remember { mutableStateOf(horariosDisponibles.firstOrNull() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {

            Text("Médico: ${medico?.nombre ?: ""}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))

            Text("Selecciona fecha", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(fechas) { fecha ->
                    FilterChip(
                        selected = fecha == fechaSeleccionada,
                        onClick = {
                            fechaSeleccionada = fecha
                            val nuevosHorarios = MedicosRepository.horariosDisponibles(medicoId, fecha)
                            horaSeleccionada = nuevosHorarios.firstOrNull() ?: ""
                        },
                        label = { Text(fecha) }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("Selecciona hora", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            if (horariosDisponibles.isEmpty()) {
                Text("No hay horarios disponibles para esta fecha.", style = MaterialTheme.typography.bodySmall)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(horariosDisponibles) { hora ->
                        FilterChip(
                            selected = hora == horaSeleccionada,
                            onClick = { horaSeleccionada = hora },
                            label = { Text(hora) }
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                enabled = horaSeleccionada.isNotEmpty(),
                onClick = {
                    navController.navigate(
                        Screen.ConfirmarCita.createRoute(medicoId, fechaSeleccionada, horaSeleccionada)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar cita")
            }
        }
    }
}