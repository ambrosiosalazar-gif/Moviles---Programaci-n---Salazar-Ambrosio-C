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

class DetallePedidoViewModel(
    private val repo: LavaRepository,
    private val pedidoId: Int
) : ViewModel() {

    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val puedeCancelar: StateFlow<Boolean> = pedido.map { p ->
        if (p == null) false
        else {
            val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
            estado == EstadoPedido.RECIBIDO
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun cancelar(onExito: () -> Unit) {
        viewModelScope.launch {
            repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
            onExito()
        }
    }
}
