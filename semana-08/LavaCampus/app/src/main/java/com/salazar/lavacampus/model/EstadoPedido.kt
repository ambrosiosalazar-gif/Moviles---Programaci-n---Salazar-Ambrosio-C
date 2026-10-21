package com.salazar.lavacampus.model

import androidx.compose.ui.graphics.Color

enum class EstadoPedido(val titulo: String, val colorHex: Long) {
    RECIBIDO("Recibido", 0xFFD97706),
    EN_PROCESO("En proceso", 0xFF2563EB),
    LISTO("Listo para recojo", 0xFF16A34A),
    ENTREGADO("Entregado", 0xFF475569),
    CANCELADO("Cancelado", 0xFFDC2626);

    val color: Color
        get() = Color(colorHex)

    fun siguiente(): EstadoPedido? = when (this) {
        RECIBIDO -> EN_PROCESO
        EN_PROCESO -> LISTO
        LISTO -> ENTREGADO
        else -> null
    }
}
