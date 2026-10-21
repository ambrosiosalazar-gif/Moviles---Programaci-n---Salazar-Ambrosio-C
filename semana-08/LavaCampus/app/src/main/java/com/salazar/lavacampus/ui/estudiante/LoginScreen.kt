package com.salazar.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.lavacampus.model.Rol
import com.salazar.lavacampus.ui.components.AppTopBar
import com.salazar.lavacampus.ui.components.LavaButton
import com.salazar.lavacampus.ui.components.LavaTextField
import com.salazar.lavacampus.viewmodel.SesionViewModel

@Composable
fun LoginScreen(
    vm: SesionViewModel,
    onLoginExitoso: (Rol) -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf(Rol.CLIENTE) }
    val errorMsg by vm.error.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Iniciar Sesión - LavaCampus") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a LavaCampus",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            TabRow(selectedTabIndex = if (rolSeleccionado == Rol.CLIENTE) 0 else 1) {
                Tab(
                    selected = rolSeleccionado == Rol.CLIENTE,
                    onClick = { rolSeleccionado = Rol.CLIENTE },
                    text = { Text("Estudiante") }
                )
                Tab(
                    selected = rolSeleccionado == Rol.ADMIN,
                    onClick = { rolSeleccionado = Rol.ADMIN },
                    text = { Text("Administrador") }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            LavaTextField(
                valor = correo,
                onValorCambio = { correo = it },
                label = "Correo electrónico"
            )
            Spacer(modifier = Modifier.height(16.dp))

            LavaTextField(
                valor = clave,
                onValorCambio = { clave = it },
                label = "Contraseña",
                visualTransformation = PasswordVisualTransformation()
            )

            if (errorMsg != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMsg!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LavaButton(
                texto = "Ingresar",
                onClick = {
                    vm.login(correo, clave, rolSeleccionado, onLoginExitoso)
                }
            )
        }
    }
}
