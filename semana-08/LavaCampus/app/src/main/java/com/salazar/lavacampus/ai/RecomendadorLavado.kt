package com.salazar.lavacampus.ai

import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.model.EstadoPedido
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class SugerenciaLavado(
    val titulo: String,
    val descripcion: String,
    val servicioSugeridoNombre: String?,
    val esUrgente: Boolean
)

object RecomendadorLavado {

    /**
     * Motor heurístico de recomendación basado en análisis de frecuencia e historial de pedidos.
     * No utiliza modelos de IA entrenados, sino análisis estadístico de patrones de consumo locales (Room).
     */
    fun calcularSugerencia(pedidos: List<PedidoConDetalle>): SugerenciaLavado? {
        val pedidosValidos = pedidos.filter { p ->
            val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
            estado != EstadoPedido.CANCELADO
        }.sortedByDescending { it.pedido.fechaRegistro }

        if (pedidosValidos.isEmpty()) {
            return null
        }

        val conteoServicios = mutableMapOf<String, Int>()
        pedidosValidos.forEach { p ->
            p.detalles.forEach { d ->
                val nombre = d.servicio.nombre
                conteoServicios[nombre] = (conteoServicios[nombre] ?: 0) + 1
            }
        }
        val servicioFavorito = conteoServicios.maxByOrNull { it.value }?.key ?: "Lavado y secado"

        var frecuenciaDias = 4L
        if (pedidosValidos.size >= 2) {
            val formatter = DateTimeFormatter.ISO_DATE
            val fechas = pedidosValidos.mapNotNull {
                try { LocalDate.parse(it.pedido.fechaRegistro, formatter) } catch (e: Exception) { null }
            }
            if (fechas.size >= 2) {
                var totalDias = 0L
                for (i in 0 until fechas.size - 1) {
                    totalDias += ChronoUnit.DAYS.between(fechas[i + 1], fechas[i])
                }
                frecuenciaDias = (totalDias / (fechas.size - 1)).coerceAtLeast(1L)
            }
        }

        val ultimoPedido = pedidosValidos.first()
        val ultimaFecha = try { LocalDate.parse(ultimoPedido.pedido.fechaRegistro, DateTimeFormatter.ISO_DATE) } catch (e: Exception) { LocalDate.now() }
        val diasTranscurridos = ChronoUnit.DAYS.between(ultimaFecha, LocalDate.now())

        val esUrgente = diasTranscurridos >= frecuenciaDias

        val descripcion = if (esUrgente) {
            "Han pasado $diasTranscurridos días desde tu último pedido. Basado en tu hábito (cada ~$frecuenciaDias días), es probable que necesites programar un lavado."
        } else {
            "Tu servicio más solicitado es '$servicioFavorito'. Todo va bien con tu ropa limpia."
        }

        return SugerenciaLavado(
            titulo = if (esUrgente) "Sugerencia Inteligente: ¡Hora de lavar!" else "Estado de tu lavandería",
            descripcion = descripcion,
            servicioSugeridoNombre = servicioFavorito,
            esUrgente = esUrgente
        )
    }
}
