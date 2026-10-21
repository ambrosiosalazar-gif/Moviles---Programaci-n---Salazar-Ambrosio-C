package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.data.local.relation.PedidoConDetalle
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(repo: LavaRepository) : ViewModel() {
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda

    private val _filtroEstado = MutableStateFlow<EstadoPedido?>(null)
    val filtroEstado: StateFlow<EstadoPedido?> = _filtroEstado

    val pedidos: StateFlow<List<PedidoConDetalle>> = combine(
        repo.observarPedidos(),
        _busqueda,
        _filtroEstado
    ) { lista, query, estadoFiltro ->
        lista.filter { p ->
            val coincideEstado = estadoFiltro == null || p.pedido.estado == estadoFiltro.name
            val coincideQuery = query.isBlank() ||
                p.usuario.nombre.contains(query, ignoreCase = true) ||
                p.pedido.id.toString().contains(query)
            coincideEstado && coincideQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun actualizarBusqueda(q: String) {
        _busqueda.value = q
    }

    fun actualizarFiltro(estado: EstadoPedido?) {
        _filtroEstado.value = estado
    }
}
