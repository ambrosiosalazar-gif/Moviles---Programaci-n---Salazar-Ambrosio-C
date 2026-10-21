package com.salazar.lavacampus.ui.estudiante

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
import com.salazar.lavacampus.model.Unidad
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.IconoServicio
import com.salazar.lavacampus.viewmodel.ServiciosViewModel

fun Servicio.textoPrecio(): String {
    return when (unidad) {
        Unidad.KG.name -> "por kg"
        Unidad.PRENDA.name -> "por prenda"
        else -> "por unidad"
    }
}

@Composable
fun TarifasScreen(
    vm: ServiciosViewModel
) {
    val servicios by vm.servicios.collectAsState()
    val activos = servicios.filter { it.activo }

    Scaffold(
        topBar = { AppTopBar(titulo = "Tarifas y Horarios") }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Información importante", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "• Horario de atención: Lunes a Sábado.")
                        Text(text = "• No atendemos los domingos (los pedidos pasan al lunes).")
                        Text(text = "• Máximo 3 pedidos activos por estudiante.")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Servicios vigentes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            items(activos, key = { it.id }) { s ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconoServicio(icono = s.icono)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(text = s.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = s.descripcion, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "S/ %.2f %s • Entrega en %dh".format(s.precio, s.textoPrecio(), s.horasEntrega),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
