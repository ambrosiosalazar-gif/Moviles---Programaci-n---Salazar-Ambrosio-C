package com.salazar.lavacampus

import com.salazar.lavacampus.ai.RecomendadorLavado
import com.salazar.lavacampus.data.local.entity.DetallePedido
import com.salazar.lavacampus.data.local.entity.Pedido
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.data.local.entity.Usuario
import com.salazar.lavacampus.data.local.relation.DetalleConServicio
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class RecomendadorLavadoTest {

    @Test
    fun testSinPedidosRetornaNull() {
        val sugerencia = RecomendadorLavado.calcularSugerencia(emptyList())
        assertNull(sugerencia)
    }

    @Test
    fun testConPedidosCalculaSugerencia() {
        val usuario = Usuario(1, "Ana", "U1", "ana@test.com", "123", "CLIENTE")
        val servicio = Servicio(1, "Lavado y secado", "Desc", "KG", 8.0, 24, true, "wash")
        
        val pedido = Pedido(1, 1, LocalDate.now().minusDays(5).toString(), LocalDate.now().minusDays(4).toString(), "ENTREGADO", 16.0, "C-01", true, "")
        val detalle = DetallePedido(1, 1, 1, 2.0, 8.0, 16.0)
        val detalleConServicio = DetalleConServicio(detalle, servicio)

        val pedidoConDetalle = PedidoConDetalle(pedido, usuario, listOf(detalleConServicio))

        val sugerencia = RecomendadorLavado.calcularSugerencia(listOf(pedidoConDetalle))
        assertNotNull(sugerencia)
        assertEquals("Lavado y secado", sugerencia?.servicioSugeridoNombre)
    }
}
