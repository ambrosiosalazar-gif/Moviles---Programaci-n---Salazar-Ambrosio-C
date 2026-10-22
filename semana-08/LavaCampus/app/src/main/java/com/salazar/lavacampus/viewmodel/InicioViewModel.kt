package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.ai.RecomendadorLavado
import com.salazar.lavacampus.ai.SugerenciaLavado
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class InicioViewModel(
    repo: LavaRepository,
    usuarioId: Int
) : ViewModel() {

    val pedidoActivo: StateFlow<PedidoConDetalle?> = repo.observarPedidosDeUsuario(usuarioId)
        .map { lista ->
            lista.firstOrNull { p ->
                val estado = try { EstadoPedido.valueOf(p.pedido.estado) } catch (e: Exception) { null }
                estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.EN_PROCESO || estado == EstadoPedido.LISTO
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val recientes: StateFlow<List<PedidoConDetalle>> = repo.observarPedidosDeUsuario(usuarioId)
        .map { lista -> lista.take(2) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sugerenciaIA: StateFlow<SugerenciaLavado?> = repo.observarPedidosDeUsuario(usuarioId)
        .map { lista -> RecomendadorLavado.calcularSugerencia(lista) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
