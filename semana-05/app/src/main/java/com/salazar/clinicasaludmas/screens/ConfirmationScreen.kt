package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.model.Cita
import com.salazar.clinicasaludmas.navigation.Screen
import java.util.UUID

@Composable
fun ConfirmationScreen(navController: NavController, medicoId: String, fecha: String, hora: String) {
    val medico = MedicosRepository.obtenerMedico(medicoId)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Confirmación de cita", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Médico: ${medico?.nombre ?: ""}")
            Text("Especialidad: ${medico?.especialidadNombre ?: ""}")
            Text("Fecha: $fecha")
            Text("Hora: $hora")
        }

        Button(
            onClick = {
                val cita = Cita(
                    id = UUID.randomUUID().toString(),
                    usuarioId = MedicosRepository.usuarioActual?.id ?: "1",
                    medicoId = medicoId,
                    nombreMedico = medico?.nombre ?: "",
                    especialidadNombre = medico?.especialidadNombre ?: "",
                    fecha = fecha,
                    hora = hora
                )
                MedicosRepository.agendarCita(cita)

                // Aplica popUpTo para cerrar el flujo de agendamiento
                navController.navigate(Screen.CitaExitosa.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agendar Cita")
        }
    }
}