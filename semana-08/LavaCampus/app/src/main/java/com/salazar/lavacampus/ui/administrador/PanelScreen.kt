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
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.graficos.GraficoBarras
import com.salazar.lavacampus.ui.components.graficos.GraficoDona
import com.salazar.lavacampus.viewmodel.PanelViewModel

@Composable
fun PanelScreen(
    vm: PanelViewModel,
    onVerBandeja: () -> Unit
) {
    val pedidosHoy by vm.pedidosHoy.collectAsState()
    val enProceso by vm.enProceso.collectAsState()
    val porRecoger by vm.porRecoger.collectAsState()
    val ingresosSemana by vm.ingresosSemana.collectAsState()
    val porEstado by vm.porEstado.collectAsState()
    val porServicio by vm.porServicio.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Panel de Administrador") }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (porRecoger > 0) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "¡Hay $porRecoger pedidos listos esperando recojo!", fontWeight = FontWeight.Bold)
                            }
                            Button(onClick = onVerBandeja) {
                                Text("Ver")
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Hoy", fontSize = 12.sp)
                            Text(text = "$pedidosHoy", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "En proceso", fontSize = 12.sp)
                            Text(text = "$enProceso", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Por recoger", fontSize = 12.sp)
                            Text(text = "$porRecoger", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.tertiary)
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
                        Text(text = "Ingresos de la semana", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "S/ %.2f".format(ingresosSemana), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Pedidos por Estado", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        GraficoDona(datos = porEstado)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Ingresos por Servicio", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        GraficoBarras(datos = porServicio)
                    }
                }
            }
        }
    }
}
