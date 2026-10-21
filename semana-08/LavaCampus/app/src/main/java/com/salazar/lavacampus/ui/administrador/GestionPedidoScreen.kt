package com.salazar.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import com.salazar.lavacampus.model.Casilleros
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.EstadoPedidoChip
import com.salazar.lavacampus.ui.components.StepperEstadoVertical
import com.salazar.lavacampus.viewmodel.GestionPedidoViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GestionPedidoScreen(
    vm: GestionPedidoViewModel,
    onVolver: () -> Unit
) {
    val pedidoConDetalle by vm.pedido.collectAsState()
    val casillerosOcupados by vm.casillerosOcupados.collectAsState()
    val casilleroElegido by vm.casilleroElegido.collectAsState()
    val errorMsg by vm.mensajeError.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Gestión de Pedido", onVolver = onVolver) }
    ) { padding ->
        if (pedidoConDetalle == null) {
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
                Text("Cargando pedido...")
            }
        } else {
            val p = pedidoConDetalle!!
            val estado = EstadoPedido.valueOf(p.pedido.estado)
            val siguiente = estado.siguiente()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Pedido #${p.pedido.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Spacer(modifier = Modifier.weight(1f))
                                EstadoPedidoChip(estado = estado)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Estudiante: ${p.usuario.nombre} (${p.usuario.codigo})", fontWeight = FontWeight.Bold)
                            Text(text = "Fecha: ${p.pedido.fechaRegistro} • Entrega: ${p.pedido.fechaEntrega}")
                        }
                    }
                }

                item {
                    Text(text = "Progreso del pedido", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    StepperEstadoVertical(estadoActual = estado)
                }

                if (siguiente == EstadoPedido.LISTO) {
                    item {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Seleccionar Casillero Libre", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Casilleros.todos.forEach { c ->
                                        val ocupado = casillerosOcupados.contains(c) && c != p.pedido.casillero
                                        FilterChip(
                                            selected = casilleroElegido == c || p.pedido.casillero == c,
                                            onClick = { if (!ocupado) vm.elegirCasillero(c) },
                                            label = { Text(c) },
                                            enabled = !ocupado
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Total: S/ %.2f".format(p.pedido.total), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.weight(1f))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Cobrado", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Switch(
                                        checked = p.pedido.pagado,
                                        onCheckedChange = { vm.marcarPagado(it) }
                                    )
                                }
                            }
                        }
                    }
                }

                if (errorMsg != null) {
                    item {
                        Text(text = errorMsg!!, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                    }
                }

                if (siguiente != null) {
                    item {
                        Button(
                            onClick = { vm.avanzarEstado(onVolver) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Avanzar a ${siguiente.titulo}")
                        }
                    }
                }

                if (estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO) {
                    item {
                        Button(
                            onClick = { vm.cancelar(onVolver) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Cancelar pedido")
                        }
                    }
                }
            }
        }
    }
}
