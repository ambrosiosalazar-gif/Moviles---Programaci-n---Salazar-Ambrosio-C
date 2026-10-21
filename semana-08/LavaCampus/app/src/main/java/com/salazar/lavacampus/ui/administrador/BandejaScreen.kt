package com.salazar.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.LavaTextField
import com.salazar.lavacampus.ui.components.PedidoCard
import com.salazar.lavacampus.viewmodel.BandejaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BandejaScreen(
    vm: BandejaViewModel,
    onGestionarPedido: (Int) -> Unit
) {
    val pedidos by vm.pedidos.collectAsState()
    val busqueda by vm.busqueda.collectAsState()
    val filtroEstado by vm.filtroEstado.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Bandeja de Pedidos") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LavaTextField(
                valor = busqueda,
                onValorCambio = { vm.actualizarBusqueda(it) },
                label = "Buscar por estudiante o ID..."
            )

            ScrollableTabRow(
                selectedTabIndex = when (filtroEstado) {
                    null -> 0
                    EstadoPedido.RECIBIDO -> 1
                    EstadoPedido.EN_PROCESO -> 2
                    EstadoPedido.LISTO -> 3
                    EstadoPedido.ENTREGADO -> 4
                    else -> 0
                },
                edgePadding = 0.dp
            ) {
                FilterChip(
                    selected = filtroEstado == null,
                    onClick = { vm.actualizarFiltro(null) },
                    label = { Text("Todos") }
                )
                FilterChip(
                    selected = filtroEstado == EstadoPedido.RECIBIDO,
                    onClick = { vm.actualizarFiltro(EstadoPedido.RECIBIDO) },
                    label = { Text("Recibido") }
                )
                FilterChip(
                    selected = filtroEstado == EstadoPedido.EN_PROCESO,
                    onClick = { vm.actualizarFiltro(EstadoPedido.EN_PROCESO) },
                    label = { Text("En proceso") }
                )
                FilterChip(
                    selected = filtroEstado == EstadoPedido.LISTO,
                    onClick = { vm.actualizarFiltro(EstadoPedido.LISTO) },
                    label = { Text("Listo") }
                )
                FilterChip(
                    selected = filtroEstado == EstadoPedido.ENTREGADO,
                    onClick = { vm.actualizarFiltro(EstadoPedido.ENTREGADO) },
                    label = { Text("Entregado") }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pedidos, key = { it.pedido.id }) { p ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Estudiante: ${p.usuario.nombre}", style = MaterialTheme.typography.titleMedium)
                            PedidoCard(
                                pedidoId = p.pedido.id,
                                fecha = p.pedido.fechaRegistro,
                                total = p.pedido.total,
                                estado = EstadoPedido.valueOf(p.pedido.estado),
                                onClick = { onGestionarPedido(p.pedido.id) },
                                casillero = p.pedido.casillero
                            )
                        }
                    }
                }
            }
        }
    }
}
