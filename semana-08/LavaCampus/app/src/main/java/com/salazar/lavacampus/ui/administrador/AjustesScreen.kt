package com.salazar.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.ui.components.AppTopBar

@Composable
fun AjustesScreen(
    onCerrarSesion: () -> Unit,
    onCambiarPerfil: () -> Unit
) {
    var recibirPedidos by remember { mutableStateOf(true) }
    var modoOscuro by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppTopBar(titulo = "Ajustes de Administrador") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "Configuración del Sistema", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Recibir nuevos pedidos")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = recibirPedidos, onCheckedChange = { recibirPedidos = it })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Modo oscuro")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = modoOscuro, onCheckedChange = { modoOscuro = it })
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onCambiarPerfil,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cambiar de perfil")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
