package com.tecsup.avisoscampus.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.model.Aviso

@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "Historial",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            if (avisos.isEmpty()) {
                Text("Sin avisos todavía", modifier = Modifier.padding(top = 8.dp))
            } else {
                avisos.take(20).forEach { a ->
                    Column(Modifier.padding(top = 12.dp)) {
                        Text(
                            a.origen.texto,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(a.titulo, fontWeight = FontWeight.Bold)
                        Text(a.texto, style = MaterialTheme.typography.bodyMedium)
                        Text(a.hora, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}