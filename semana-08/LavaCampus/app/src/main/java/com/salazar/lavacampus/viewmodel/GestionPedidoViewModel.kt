package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GestionPedidoViewModel(
    private val repo: LavaRepository,
    private val pedidoId: Int
) : ViewModel() {

    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val casillerosOcupados: StateFlow<List<String>> = repo.observarCasillerosOcupados()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _casilleroElegido = MutableStateFlow<String?>(null)
    val casilleroElegido: StateFlow<String?> = _casilleroElegido

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    fun elegirCasillero(c: String) {
        _casilleroElegido.value = c
    }

    fun marcarPagado(pagado: Boolean) {
        viewModelScope.launch {
            repo.marcarPagado(pedidoId, pagado)
        }
    }

    fun avanzarEstado(onExito: () -> Unit) {
        viewModelScope.launch {
            val p = repo.observarPedido(pedidoId).let { flow ->
                var current: PedidoConDetalle? = null
                flow.collect { current = it; return@collect }
                current
            } ?: return@launch

            val estadoActual = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
            val siguiente = estadoActual?.siguiente()

            if (siguiente == null) {
                _mensajeError.value = "El pedido ya se encuentra en un estado final"
                return@launch
            }

            if (siguiente == EstadoPedido.LISTO) {
                val casillero = _casilleroElegido.value
                if (casillero.isNullOrBlank()) {
                    _mensajeError.value = "Debe seleccionar un casillero libre"
                    return@launch
                }
            }

            if (siguiente == EstadoPedido.ENTREGADO) {
                if (!p.pedido.pagado) {
                    _mensajeError.value = "El pedido debe estar pagado para ser entregado"
                    return@launch
                }
            }

            repo.cambiarEstado(pedidoId, siguiente, if (siguiente == EstadoPedido.LISTO) _casilleroElegido.value else p.pedido.casillero)
            _mensajeError.value = null
            onExito()
        }
    }

    fun cancelar(onExito: () -> Unit) {
        viewModelScope.launch {
            val p = repo.observarPedido(pedidoId).let { flow ->
                var current: PedidoConDetalle? = null
                flow.collect { current = it; return@collect }
                current
            } ?: return@launch

            val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
            if (estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO) {
                repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
                _mensajeError.value = null
                onExito()
            } else {
                _mensajeError.value = "No se puede cancelar un pedido en estado ${estado?.titulo}"
            }
        }
    }
}
