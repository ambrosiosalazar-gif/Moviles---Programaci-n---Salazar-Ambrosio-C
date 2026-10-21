package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MisPedidosViewModel(
    private val repo: LavaRepository,
    usuarioId: Int
) : ViewModel() {

    val activos: StateFlow<List<PedidoConDetalle>> = repo.observarPedidosDeUsuario(usuarioId)
        .map { lista ->
            lista.filter { p ->
                val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
                estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO || estado == EstadoPedido.LISTO
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val historial: StateFlow<List<PedidoConDetalle>> = repo.observarPedidosDeUsuario(usuarioId)
        .map { lista ->
            lista.filter { p ->
                val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
                estado == EstadoPedido.ENTREGADO || estado == EstadoPedido.CANCELADO
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cancelar(pedidoId: Int) {
        viewModelScope.launch {
            repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
        }
    }
}
