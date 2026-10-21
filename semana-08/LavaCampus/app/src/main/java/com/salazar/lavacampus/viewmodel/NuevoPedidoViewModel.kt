package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.LineaPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class NuevoPedidoViewModel(
    private val repo: LavaRepository,
    private val usuarioId: Int
) : ViewModel() {

    val serviciosActivos: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _cantidades = MutableStateFlow<Map<Int, Double>>(emptyMap())
    val cantidades: StateFlow<Map<Int, Double>> = _cantidades

    private val _nota = MutableStateFlow("")
    val nota: StateFlow<String> = _nota

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    fun cambiarCantidad(servicioId: Int, cantidad: Double) {
        val current = _cantidades.value.toMutableMap()
        if (cantidad <= 0.0) {
            current.remove(servicioId)
        } else {
            current[servicioId] = cantidad
        }
        _cantidades.value = current
    }

    fun actualizarNota(n: String) {
        _nota.value = n
    }

    fun calcularTotal(servicios: List<Servicio>): Double {
        val cant = _cantidades.value
        return servicios.sumOf { s ->
            val c = cant[s.id] ?: 0.0
            c * s.precio
        }
    }

    fun calcularFechaEntrega(servicios: List<Servicio>): String {
        val cant = _cantidades.value
        val maxHoras = servicios.filter { (cant[it.id] ?: 0.0) > 0.0 }
            .maxOfOrNull { it.horasEntrega } ?: 24
        val dias = maxHoras / 24
        var fecha = LocalDate.now().plusDays(dias.toLong())
        if (fecha.dayOfWeek == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1)
        }
        return fecha.toString()
    }

    fun confirmarPedido(servicios: List<Servicio>, onExito: () -> Unit) {
        viewModelScope.launch {
            val activos = repo.contarActivos(usuarioId)
            if (activos >= 3) {
                _mensajeError.value = "Ya tienes 3 pedidos activos"
                return@launch
            }

            val cant = _cantidades.value
            val lineas = cant.map { (id, cantidad) ->
                LineaPedido(servicioId = id, cantidad = cantidad)
            }

            if (lineas.isEmpty()) {
                _mensajeError.value = "Selecciona al menos un servicio"
                return@launch
            }

            val fechaEntrega = calcularFechaEntrega(servicios)
            repo.crearPedido(usuarioId, lineas, fechaEntrega, _nota.value)
            _mensajeError.value = null
            onExito()
        }
    }
}
