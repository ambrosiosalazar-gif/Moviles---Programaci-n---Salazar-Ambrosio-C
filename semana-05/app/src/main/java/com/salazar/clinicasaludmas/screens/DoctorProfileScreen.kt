package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorProfileScreen(navController: NavController, especialidadId: String) {
    val medico = MedicosRepository.obtenerMedico(especialidadId)
        ?: MedicosRepository.medicos.firstOrNull()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del médico") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (medico == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Médico no encontrado")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(medico.nombre, style = MaterialTheme.typography.headlineSmall)
                Text(
                    "${medico.especialidadNombre} · CMP: ${medico.CMP}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107))
                    Text(" ${medico.calificacion}")
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Especialista comprometido con la atención médica de calidad para todos sus pacientes.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {
                        navController.navigate(Screen.FechaHora.createRoute(medico.id))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agendar cita")
                }
            }
        }
    }
}