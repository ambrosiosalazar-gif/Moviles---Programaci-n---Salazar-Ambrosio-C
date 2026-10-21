package com.salazar.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.lavacampus.data.prefs.PreferenciasStore
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: LavaRepository,
    private val prefsStore: PreferenciasStore
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val perfilActivo: StateFlow<String> = prefsStore.perfilActivo
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val usuarioId: StateFlow<Int> = prefsStore.usuarioId
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val modoOscuro: StateFlow<Boolean> = prefsStore.modoOscuro
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun login(correo: String, clave: String, rolSeleccionado: Rol, onExito: (Rol) -> Unit) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Complete todos los campos"
            return
        }

        viewModelScope.launch {
            val usuario = repo.login(correo.trim(), clave)
            if (usuario == null) {
                _error.value = "Correo o contraseña incorrectos"
                return@launch
            }

            if (usuario.rol != rolSeleccionado.name) {
                _error.value = "El usuario no corresponde al rol seleccionado"
                return@launch
            }

            _error.value = null
            prefsStore.guardarSesion(usuario.rol, usuario.id)
            onExito(Rol.valueOf(usuario.rol))
        }
    }
}
