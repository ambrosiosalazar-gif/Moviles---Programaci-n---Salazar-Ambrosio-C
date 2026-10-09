package com.salazar.clinicasaludmas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salazar.clinicasaludmas.data.MedicosRepository
import com.salazar.clinicasaludmas.navigation.Screen

@Composable
fun EspecialidadesScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val especialidades = MedicosRepository.buscarEspecialidades(query)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selección de especialidad", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar especialidad...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(especialidades) { espec ->
                Card(
                    onClick = { navController.navigate(Screen.Medicos.createRoute(espec.id)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(espec.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(espec.descripcion, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}