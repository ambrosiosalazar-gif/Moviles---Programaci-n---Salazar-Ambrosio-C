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
import com.salazar.lavacampus.model.Unidad
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.IconoServicio
import com.salazar.lavacampus.ui.components.LavaButton
import com.salazar.lavacampus.ui.components.LavaTextField
import com.salazar.lavacampus.ui.components.SelectorCantidad
import com.salazar.lavacampus.ui.components.SmartNotesChips
import com.salazar.lavacampus.viewmodel.NuevoPedidoViewModel

@Composable
fun NuevoPedidoScreen(
    vm: NuevoPedidoViewModel,
    onVolver: () -> Unit,
    onPedidoCreado: () -> Unit
) {
    val servicios by vm.serviciosActivos.collectAsState()
    val cantidades by vm.cantidades.collectAsState()
    val nota by vm.nota.collectAsState()
    val errorMsg by vm.mensajeError.collectAsState()

    val total = vm.calcularTotal(servicios)
    val fechaEntrega = vm.calcularFechaEntrega(servicios)
    val hayServicios = cantidades.isNotEmpty() && cantidades.values.any { it > 0.0 }

    Scaffold(
        topBar = { AppTopBar(titulo = "Nuevo Pedido", onVolver = onVolver) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(servicios, key = { it.id }) { s ->
                    val cantidad = cantidades[s.id] ?: 0.0
                    val paso = if (s.unidad == Unidad.KG.name) 0.5 else 1.0
                    val max = if (s.unidad == Unidad.KG.name) 10.0 else 30.0

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconoServicio(icono = s.icono)
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Text(text = s.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(text = "S/ %.2f (%s)".format(s.precio, s.unidad), fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            SelectorCantidad(
                                valor = cantidad,
                                paso = paso,
                                minimo = 0.0,
                                maximo = max,
                                onCambiar = { vm.cambiarCantidad(s.id, it) }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    LavaTextField(
                        valor = nota,
                        onValorCambio = { vm.actualizarNota(it) },
                        label = "Nota adicional (opcional)"
                    )
                    if (nota.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Etiquetas detectadas por IA:", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                        SmartNotesChips(nota = nota)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Total a pagar:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "S/ %.2f".format(total), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Entrega estimada: $fechaEntrega", fontSize = 14.sp)
                }
            }

            if (errorMsg != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMsg!!, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            LavaButton(
                texto = "Confirmar pedido",
                onClick = {
                    vm.confirmarPedido(servicios, onPedidoCreado)
                },
                enabled = hayServicios
            )
        }
    }
}
