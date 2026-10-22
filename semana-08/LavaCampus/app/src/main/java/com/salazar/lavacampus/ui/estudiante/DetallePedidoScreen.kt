package com.salazar.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.EstadoPedidoChip
import com.salazar.lavacampus.ui.components.SmartNotesChips
import com.salazar.lavacampus.ui.components.StepperEstadoVertical
import com.salazar.lavacampus.viewmodel.DetallePedidoViewModel

@Composable
fun DetallePedidoScreen(
    vm: DetallePedidoViewModel,
    onVolver: () -> Unit
) {
    val pedidoConDetalle by vm.pedido.collectAsState()
    val puedeCancelar by vm.puedeCancelar.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppTopBar(titulo = "Detalle del Pedido", onVolver = onVolver) }
    ) { padding ->
        if (pedidoConDetalle == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Cargando pedido...")
            }
        } else {
            val p = pedidoConDetalle!!
            val estado = EstadoPedido.valueOf(p.pedido.estado)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pedido #${p.pedido.id}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        EstadoPedidoChip(estado = estado)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Fecha de registro: ${p.pedido.fechaRegistro}", fontSize = 14.sp)
                    Text(text = "Entrega estimada: ${p.pedido.fechaEntrega}", fontSize = 14.sp)
                    if (!p.pedido.nota.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Nota: ${p.pedido.nota}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        SmartNotesChips(nota = p.pedido.nota)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(text = "Seguimiento", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    StepperEstadoVertical(estadoActual = estado)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(text = "Servicios solicitados", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(p.detalles, key = { it.detalle.id }) { d ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = d.servicio.nombre, fontWeight = FontWeight.Bold)
                                Text(text = "Cantidad: ${d.detalle.cantidad} x S/ %.2f".format(d.detalle.precioUnitario), fontSize = 12.sp)
                            }
                            Text(
                                text = "S/ %.2f".format(d.detalle.subtotal),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Total:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "S/ %.2f".format(p.pedido.total), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (p.pedido.pagado) "Pagado" else "Pago pendiente, se paga al recoger",
                                fontWeight = FontWeight.Bold,
                                color = if (p.pedido.pagado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                            if (p.pedido.casillero != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Casillero asignado: ${p.pedido.casillero}", fontWeight = FontWeight.Bold)
                            } else {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Casillero: Se asignará al estar listo", fontSize = 14.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { mostrarDialogo = true },
                        enabled = puedeCancelar,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cancelar pedido")
                    }
                    if (!puedeCancelar) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Solo se cancela antes de empezar el lavado",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = { Text("Cancelar pedido") },
                    text = { Text("¿Estás seguro de que deseas cancelar este pedido?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogo = false
                                vm.cancelar(onVolver)
                            }
                        ) {
                            Text("Sí, cancelar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarDialogo = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}
