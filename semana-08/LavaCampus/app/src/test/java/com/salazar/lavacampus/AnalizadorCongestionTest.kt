package com.salazar.lavacampus

import com.salazar.lavacampus.ai.AnalizadorCongestion
import com.salazar.lavacampus.ai.NivelCongestion
import com.salazar.lavacampus.data.local.entity.Pedido
import com.salazar.lavacampus.data.local.entity.Usuario
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import org.junit.Assert.assertEquals
import org.junit.Test

class AnalizadorCongestionTest {

    @Test
    fun testSinPedidosActivosEsBajo() {
        val diagnostico = AnalizadorCongestion.evaluarCongestion(emptyList())
        assertEquals(NivelCongestion.BAJO, diagnostico.nivel)
        assertEquals(0, diagnostico.totalActivos)
    }

    @Test
    fun testPocosPedidosEsBajo() {
        val usuario = Usuario(1, "Ana", "U1", "ana@test.com", "123", "CLIENTE")
        val pedidos = (1..2).map { id ->
            PedidoConDetalle(
                pedido = Pedido(id = id, usuarioId = 1, fechaRegistro = "2026-10-21", fechaEntrega = "2026-10-22", estado = "RECIBIDO", total = 10.0, casillero = null, pagado = false, nota = ""),
                usuario = usuario,
                detalles = emptyList()
            )
        }
        val diagnostico = AnalizadorCongestion.evaluarCongestion(pedidos)
        assertEquals(NivelCongestion.BAJO, diagnostico.nivel)
        assertEquals(2, diagnostico.totalActivos)
    }

    @Test
    fun testPedidosModeradosEsModerado() {
        val usuario = Usuario(1, "Ana", "U1", "ana@test.com", "123", "CLIENTE")
        val pedidos = (1..5).map { id ->
            PedidoConDetalle(
                pedido = Pedido(id = id, usuarioId = 1, fechaRegistro = "2026-10-21", fechaEntrega = "2026-10-22", estado = "EN_PROCESO", total = 10.0, casillero = null, pagado = false, nota = ""),
                usuario = usuario,
                detalles = emptyList()
            )
        }
        val diagnostico = AnalizadorCongestion.evaluarCongestion(pedidos)
        assertEquals(NivelCongestion.MODERADO, diagnostico.nivel)
        assertEquals(5, diagnostico.totalActivos)
    }

    @Test
    fun testMuchosPedidosEsAlto() {
        val usuario = Usuario(1, "Ana", "U1", "ana@test.com", "123", "CLIENTE")
        val pedidos = (1..9).map { id ->
            PedidoConDetalle(
                pedido = Pedido(id = id, usuarioId = 1, fechaRegistro = "2026-10-21", fechaEntrega = "2026-10-22", estado = "LISTO", total = 10.0, casillero = "C-0$id", pagado = true, nota = ""),
                usuario = usuario,
                detalles = emptyList()
            )
        }
        val diagnostico = AnalizadorCongestion.evaluarCongestion(pedidos)
        assertEquals(NivelCongestion.ALTO, diagnostico.nivel)
        assertEquals(9, diagnostico.totalActivos)
    }
}
