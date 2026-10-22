package com.salazar.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.PedidoCard
import com.salazar.lavacampus.ui.components.StepperEstadoHorizontal
import com.salazar.lavacampus.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    vm: InicioViewModel,
    onNuevoPedido: () -> Unit,
    onDetallePedido: (Int) -> Unit
) {
    val pedidoActivo by vm.pedidoActivo.collectAsState()
    val recientes by vm.recientes.collectAsState()
    val sugerenciaIA by vm.sugerenciaIA.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "LavaCampus - Estudiante") }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Button(
                    onClick = onNuevoPedido,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Nuevo pedido")
                }
            }

            if (sugerenciaIA != null) {
                item {
                    val sug = sugerenciaIA!!
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (sug.esUrgente) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "🤖 ${sug.titulo}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = sug.descripcion, fontSize = 14.sp)
                            if (sug.servicioSugeridoNombre != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Servicio recomendado: ${sug.servicioSugeridoNombre}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Pedido Activo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (pedidoActivo != null) {
                    val p = pedidoActivo!!
                    val estado = EstadoPedido.valueOf(p.pedido.estado)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Pedido #${p.pedido.id} - S/ %.2f".format(p.pedido.total),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            StepperEstadoHorizontal(estadoActual = estado)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Entrega estimada: ${p.pedido.fechaEntrega}")
                            if (estado == EstadoPedido.LISTO && p.pedido.casillero != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "¡Recógelo en el casillero ${p.pedido.casillero}!",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = "No tienes pedidos activos",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Últimos Pedidos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            items(recientes, key = { it.pedido.id }) { p ->
                PedidoCard(
                    pedidoId = p.pedido.id,
                    fecha = p.pedido.fechaRegistro,
                    total = p.pedido.total,
                    estado = EstadoPedido.valueOf(p.pedido.estado),
                    onClick = { onDetallePedido(p.pedido.id) },
                    casillero = p.pedido.casillero
                )
            }
        }
    }
}
