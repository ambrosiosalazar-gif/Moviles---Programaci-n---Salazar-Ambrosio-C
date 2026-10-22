package com.salazar.lavacampus

import com.salazar.lavacampus.ai.AnalizadorNotas
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnalizadorNotasTest {

    @Test
    fun testNotaVacia() {
        val etiquetas = AnalizadorNotas.extraerEtiquetas("")
        assertTrue(etiquetas.isEmpty())
    }

    @Test
    fun testNotaNormalUnicaEtiqueta() {
        val etiquetas = AnalizadorNotas.extraerEtiquetas("Por favor lavar con agua fría")
        assertEquals(1, etiquetas.size)
        assertTrue(etiquetas.contains("Agua fría"))
    }

    @Test
    fun testMultiplesEtiquetasYMayusculasTildes() {
        val etiquetas = AnalizadorNotas.extraerEtiquetas("URGENTE: Ropa muy DELICADA y agua fría por favor")
        assertEquals(3, etiquetas.size)
        assertTrue(etiquetas.contains("Urgente"))
        assertTrue(etiquetas.contains("Ropa delicada"))
        assertTrue(etiquetas.contains("Agua fría"))
    }

    @Test
    fun testNegacionesNoDetectadas() {
        val etiquetas = AnalizadorNotas.extraerEtiquetas("El pedido no es urgente y no es delicado")
        assertTrue(etiquetas.isEmpty())
    }
}
