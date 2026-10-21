package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.data.repository.LavaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ServiciosViewModel(private val repo: LavaRepository) : ViewModel() {
    val servicios: StateFlow<List<Servicio>> = repo.observarServicios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun alternar(s: Servicio) {
        viewModelScope.launch { repo.cambiarActivo(s.id, !s.activo) }
    }

    fun guardar(servicio: Servicio, onExito: () -> Unit) {
        viewModelScope.launch {
            repo.guardarServicio(servicio)
            onExito()
        }
    }

    fun eliminar(servicio: Servicio) {
        viewModelScope.launch {
            val tiene = repo.servicioTieneDetalles(servicio.id)
            if (tiene) {
                _mensaje.value = "El servicio tiene pedidos asociados; solo se puede desactivar."
                repo.cambiarActivo(servicio.id, false)
            } else {
                repo.eliminarServicio(servicio)
                _mensaje.value = null
            }
        }
    }
}
