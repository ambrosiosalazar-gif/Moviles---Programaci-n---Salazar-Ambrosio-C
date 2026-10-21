package com.salazar.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.IconoServicio
import com.salazar.lavacampus.viewmodel.ServiciosViewModel

@Composable
fun ServiciosScreen(
    vm: ServiciosViewModel,
    onEditar: (Int) -> Unit
) {
    val servicios by vm.servicios.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Servicios") }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(servicios, key = { it.id }) { s ->
                ServicioItem(
                    servicio = s,
                    onToggle = { vm.alternar(s) },
                    onEditar = { onEditar(s.id) }
                )
            }
        }
    }
}

@Composable
fun ServicioItem(
    servicio: Servicio,
    onToggle: () -> Unit,
    onEditar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconoServicio(icono = servicio.icono)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = servicio.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = servicio.descripcion,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "S/ %.2f (%s)".format(servicio.precio, servicio.unidad),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Switch(
                checked = servicio.activo,
                onCheckedChange = { onToggle() }
            )
        }
    }
}
