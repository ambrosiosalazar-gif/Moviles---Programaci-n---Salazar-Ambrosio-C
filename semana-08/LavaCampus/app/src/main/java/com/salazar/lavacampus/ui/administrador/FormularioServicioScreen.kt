package com.salazar.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salazar.lavacampus.data.local.entity.Servicio
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.LavaTextField
import com.salazar.lavacampus.viewmodel.ServiciosViewModel

@Composable
fun FormularioServicioScreen(
    vm: ServiciosViewModel,
    servicioId: Int? = null,
    onVolver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioStr by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { AppTopBar(titulo = if (servicioId == null) "Nuevo Servicio" else "Editar Servicio", onVolver = onVolver) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LavaTextField(valor = nombre, onValorCambio = { nombre = it }, label = "Nombre del servicio")
            Spacer(modifier = Modifier.height(12.dp))
            LavaTextField(valor = descripcion, onValorCambio = { descripcion = it }, label = "Descripción")
            Spacer(modifier = Modifier.height(12.dp))
            LavaTextField(valor = precioStr, onValorCambio = { precioStr = it }, label = "Precio (S/)")
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Servicio activo")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = activo, onCheckedChange = { activo = it })
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val precio = precioStr.toDoubleOrNull() ?: 0.0
                    if (nombre.isNotBlank() && precio > 0) {
                        vm.guardar(
                            Servicio(
                                id = servicioId ?: 0,
                                nombre = nombre,
                                descripcion = descripcion,
                                unidad = "KG",
                                precio = precio,
                                horasEntrega = 24,
                                activo = activo,
                                icono = "wash"
                            ),
                            onVolver
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar servicio")
            }
        }
    }
}
