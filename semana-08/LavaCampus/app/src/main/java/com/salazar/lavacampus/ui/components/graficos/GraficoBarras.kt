package com.salazar.lavacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun GraficoBarras(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier
) {
    val maxVal = (datos.maxOfOrNull { it.valor } ?: 1.0).coerceAtLeast(1.0)

    Canvas(modifier = modifier.height(180.dp).fillMaxSize()) {
        val barWidth = size.width / (datos.size.coerceAtLeast(1) * 2)
        val h = size.height

        datos.forEachIndexed { index, item ->
            val barHeight = ((item.valor / maxVal) * h * 0.8).toFloat()
            val x = index * barWidth * 2 + barWidth / 2

            drawRoundRect(
                color = item.color,
                topLeft = Offset(x, h - barHeight),
                size = Size(barWidth, barHeight)
            )
        }
    }
}
