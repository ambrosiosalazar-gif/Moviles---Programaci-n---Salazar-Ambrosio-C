package com.salazar.lavacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun IconoServicio(
    icono: String,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier.size(40.dp)) {
        val w = size.width
        val h = size.height

        when (icono.lowercase()) {
            "iron" -> {
                drawRoundRect(
                    color = primaryColor,
                    topLeft = Offset(w * 0.15f, h * 0.45f),
                    size = Size(w * 0.7f, h * 0.35f),
                    cornerRadius = CornerRadius(w * 0.1f, h * 0.1f),
                    style = Stroke(width = 4f)
                )
                drawLine(
                    color = primaryColor,
                    start = Offset(w * 0.3f, h * 0.25f),
                    end = Offset(w * 0.7f, h * 0.25f),
                    strokeWidth = 4f
                )
            }
            "shirt" -> {
                drawRoundRect(
                    color = primaryColor,
                    topLeft = Offset(w * 0.2f, h * 0.2f),
                    size = Size(w * 0.6f, h * 0.65f),
                    cornerRadius = CornerRadius(w * 0.05f, h * 0.05f),
                    style = Stroke(width = 4f)
                )
                drawLine(
                    color = primaryColor,
                    start = Offset(w * 0.2f, h * 0.35f),
                    end = Offset(w * 0.8f, h * 0.35f),
                    strokeWidth = 4f
                )
            }
            else -> {
                drawRoundRect(
                    color = primaryColor,
                    topLeft = Offset(w * 0.15f, h * 0.15f),
                    size = Size(w * 0.7f, h * 0.7f),
                    cornerRadius = CornerRadius(w * 0.1f, h * 0.1f),
                    style = Stroke(width = 4f)
                )
                drawCircle(
                    color = primaryColor,
                    radius = w * 0.2f,
                    center = Offset(w / 2, h * 0.55f),
                    style = Stroke(width = 4f)
                )
            }
        }
    }
}
