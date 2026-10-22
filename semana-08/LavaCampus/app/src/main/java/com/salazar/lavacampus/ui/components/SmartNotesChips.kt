package com.salazar.lavacampus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.ai.AnalizadorNotas

@Composable
fun SmartNotesChips(
    nota: String?,
    modifier: Modifier = Modifier
) {
    val etiquetas = try {
        AnalizadorNotas.extraerEtiquetas(nota)
    } catch (e: Exception) {
        emptyList()
    }
    if (etiquetas.isEmpty()) return

    Row(
        modifier = modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        etiquetas.forEach { tag ->
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = "🏷️ $tag",
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
