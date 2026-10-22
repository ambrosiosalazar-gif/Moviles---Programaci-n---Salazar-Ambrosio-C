package com.salazar.lavacampus.ai

import androidx.compose.ui.graphics.Color
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.model.EstadoPedido

enum class NivelCongestion(val titulo: String, val color: Color, val descripcion: String) {
    BAJO("Bajo (Operación Normal)", Color(0xFF16A34A), "La carga de trabajo es óptima. Todo marcha según lo esperado."),
    MODERADO("Moderado (Carga Considerable)", Color(0xFFD97706), "Volumen de pedidos activo considerable. Mantener atención al ritmo de lavado."),
    ALTO("Alto (Carga Elevada)", Color(0xFFDC2626), "¡Carga elevada! Se requiere atención prioritaria y optimización de tiempos.")
}

data class DiagnosticoCongestion(
    val nivel: NivelCongestion,
    val totalActivos: Int,
    val recomendacion: String
)

object AnalizadorCongestion {

    /**
     * Analiza los pedidos actuales y calcula un índice heurístico de congestión operativa.
     * Umbrales configurables:
     * - 0 a 3 pedidos activos: BAJO
     * - 4 a 7 pedidos activos: MODERADO
     * - 8 o más pedidos activos: ALTO
     */
    fun evaluarCongestion(pedidos: List<PedidoConDetalle>): DiagnosticoCongestion {
        val activos = pedidos.filter { p ->
            val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
            estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO || estado == EstadoPedido.LISTO
        }

        val total = activos.size

        val nivel = when {
            total <= 3 -> NivelCongestion.BAJO
            total <= 7 -> NivelCongestion.MODERADO
            else -> NivelCongestion.ALTO
        }

        val recomendacion = when (nivel) {
            NivelCongestion.BAJO -> "Operación estable. No se requieren acciones adicionales."
            NivelCongestion.MODERADO -> "Considere agilizar los pedidos en proceso y revisar casilleros libres."
            NivelCongestion.ALTO -> "¡Atención! Alta acumulación de ropa. Asigne personal prioritario a planchado y lavado."
        }

        return DiagnosticoCongestion(
            nivel = nivel,
            totalActivos = total,
            recomendacion = recomendacion
        )
    }
}
