package com.salazar.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.PedidoCard
import com.salazar.lavacampus.viewmodel.MisPedidosViewModel

@Composable
fun MisPedidosScreen(
    vm: MisPedidosViewModel,
    onDetallePedido: (Int) -> Unit,
    onNuevoPedido: () -> Unit
) {
    var pestaña by remember { mutableIntStateOf(0) }
    val activos by vm.activos.collectAsState()
    val historial by vm.historial.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Mis Pedidos") },
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevoPedido) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo pedido")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = pestaña) {
                Tab(
                    selected = pestaña == 0,
                    onClick = { pestaña = 0 },
                    text = { Text("Activos (${activos.size})") }
                )
                Tab(
                    selected = pestaña == 1,
                    onClick = { pestaña = 1 },
                    text = { Text("Historial (${historial.size})") }
                )
            }

            val lista = if (pestaña == 0) activos else historial

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lista, key = { it.pedido.id }) { p ->
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
}
