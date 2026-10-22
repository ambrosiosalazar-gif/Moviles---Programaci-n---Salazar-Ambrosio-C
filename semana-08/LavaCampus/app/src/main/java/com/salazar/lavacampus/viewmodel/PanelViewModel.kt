package com.salazar.lavacampus.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.ai.AnalizadorCongestion
import com.salazar.lavacampus.ai.DiagnosticoCongestion
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.EstadoPedido
import com.salazar.lavacampus.ui.components.graficos.DatoGrafico
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class PanelViewModel(repo: LavaRepository) : ViewModel() {
    private val hoyStr = LocalDate.now().toString()
    private val inicioSemanaStr = LocalDate.now()
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .toString()

    val pedidosHoy: StateFlow<Int> = repo.observarPedidosDeFecha(hoyStr)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val enProceso: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val porRecoger: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.LISTO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val ingresosSemana: StateFlow<Double> = repo.observarIngresosDesde(inicioSemanaStr)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val diagnosticoCongestion: StateFlow<DiagnosticoCongestion> = repo.observarPedidos()
        .map { AnalizadorCongestion.evaluarCongestion(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AnalizadorCongestion.evaluarCongestion(emptyList()))

    val porEstado: StateFlow<List<DatoGrafico>> = combine(
        repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO),
        repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO),
        repo.observarCantidadPorEstado(EstadoPedido.LISTO),
        repo.observarCantidadPorEstado(EstadoPedido.ENTREGADO)
    ) { rec, proc, listo, ent ->
        listOf(
            DatoGrafico("Recibido", rec.toDouble(), EstadoPedido.RECIBIDO.color),
            DatoGrafico("En proceso", proc.toDouble(), EstadoPedido.EN_PROCESO.color),
            DatoGrafico("Listo", listo.toDouble(), EstadoPedido.LISTO.color),
            DatoGrafico("Entregado", ent.toDouble(), EstadoPedido.ENTREGADO.color)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val porServicio: StateFlow<List<DatoGrafico>> = repo.observarIngresosPorServicio(inicioSemanaStr)
        .map { lista ->
            val colores = listOf(Color(0xFF0891B2), Color(0xFF6D28D9), Color(0xFF16A34A), Color(0xFFD97706))
            lista.mapIndexed { index, ing ->
                DatoGrafico(ing.nombre, ing.total, colores[index % colores.size])
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
