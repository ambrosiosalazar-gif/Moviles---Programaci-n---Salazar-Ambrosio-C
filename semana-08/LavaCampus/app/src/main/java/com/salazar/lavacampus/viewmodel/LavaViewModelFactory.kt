package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salazar.lavacampus.data.prefs.PreferenciasStore
import com.salazar.lavacampus.data.repository.LavaRepository

class LavaViewModelFactory(
    private val repo: LavaRepository,
    private val prefsStore: PreferenciasStore,
    private val extraId: Int = 0
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (SesionViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return SesionViewModel(repo, prefsStore) as T
        }
        if (ServiciosViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return ServiciosViewModel(repo) as T
        }
        if (InicioViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return InicioViewModel(repo, extraId) as T
        }
        if (NuevoPedidoViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return NuevoPedidoViewModel(repo, extraId) as T
        }
        if (DetallePedidoViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return DetallePedidoViewModel(repo, extraId) as T
        }
        if (MisPedidosViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return MisPedidosViewModel(repo, extraId) as T
        }
        if (PanelViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return PanelViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
