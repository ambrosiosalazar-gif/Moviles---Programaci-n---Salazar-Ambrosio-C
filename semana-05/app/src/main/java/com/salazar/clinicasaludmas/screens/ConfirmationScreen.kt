package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.model.Cita
import com.salazar.clinicasaludmas.navigation.Screen

@Composable
fun ConfirmationScreen(navController: NavController, cita: Cita?) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(72.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text("¡Cita agendada!", style = MaterialTheme.typography.headlineSmall)

        cita?.let {
            Spacer(Modifier.height(8.dp))
            Text(it.medicoNombre, style = MaterialTheme.typography.titleMedium)
            Text("${it.fecha}, ${it.hora}", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            navController.navigate(Screen.MisCitas.route) {
                popUpTo(Screen.Home.route)
            }
        }) { Text("Ver mis citas") }
    }
}
