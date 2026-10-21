package com.salazar.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.model.EstadoPedido

@Composable
fun StepperEstadoHorizontal(
    estadoActual: EstadoPedido,
    modifier: Modifier = Modifier
) {
    val etapas = listOf(EstadoPedido.RECIBIDO, EstadoPedido.EN_PROCESO, EstadoPedido.LISTO, EstadoPedido.ENTREGADO)
    val indexActual = etapas.indexOf(estadoActual)

    Column(modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            val stepWidth = size.width / (etapas.size - 1)
            val centerY = size.height / 2

            drawLine(
                color = Color.LightGray,
                start = Offset(0f, centerY),
                end = Offset(size.width, centerY),
                strokeWidth = 4f
            )

            if (indexActual > 0) {
                val progressEndX = (indexActual.coerceAtMost(etapas.size - 1)) * stepWidth
                drawLine(
                    color = estadoActual.color,
                    start = Offset(0f, centerY),
                    end = Offset(progressEndX, centerY),
                    strokeWidth = 6f
                )
            }

            etapas.forEachIndexed { index, _ ->
                val x = index * stepWidth
                val isCompleted = index <= indexActual
                drawCircle(
                    color = if (isCompleted) estadoActual.color else Color.LightGray,
                    radius = if (index == indexActual) 12f else 8f,
                    center = Offset(x, centerY)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            etapas.forEachIndexed { index, estado ->
                Text(
                    text = estado.titulo,
                    fontSize = 10.sp,
                    fontWeight = if (index == indexActual) FontWeight.Bold else FontWeight.Normal,
                    color = if (index <= indexActual) Color.Black else Color.Gray,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun StepperEstadoVertical(
    estadoActual: EstadoPedido,
    modifier: Modifier = Modifier
) {
    val etapas = listOf(EstadoPedido.RECIBIDO, EstadoPedido.EN_PROCESO, EstadoPedido.LISTO, EstadoPedido.ENTREGADO)
    val indexActual = etapas.indexOf(estadoActual)

    Column(modifier = modifier.padding(vertical = 8.dp)) {
        etapas.forEachIndexed { index, estado ->
            val isCompleted = index <= indexActual
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Canvas(modifier = Modifier.width(24.dp).height(24.dp)) {
                    drawCircle(
                        color = if (isCompleted) estadoActual.color else Color.LightGray,
                        radius = if (index == indexActual) 12f else 8f,
                        center = Offset(size.width / 2, size.height / 2)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = estado.titulo,
                    fontSize = 14.sp,
                    fontWeight = if (index == indexActual) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCompleted) Color.Unspecified else Color.Gray
                )
            }
        }
    }
}
