package com.tecsup.murocampus.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrear(
    vm: PostsViewModel,
    onAtras: () -> Unit
) {
    val estado by vm.creado.collectAsStateWithLifecycle()
    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) { vm.limpiarCreado() }
    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }
    BackHandler(onBack = onAtras)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva publicación") },
                navigationIcon = {
                    IconButton(onClick = onAtras) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { vm.publicar(titulo, contenido) },
                enabled = titulo.isNotBlank() && contenido.isNotBlank() && estado !is UiState.Cargando,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Publicar") }

            Spacer(Modifier.height(16.dp))
            when (val e = estado) {
                is UiState.Cargando -> Text("Enviando…")
                is UiState.Exito -> Text(
                    "Publicado con id ${e.datos.id}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
                is UiState.Error -> Text(e.mensaje, color = MaterialTheme.colorScheme.error)
                null -> {}
            }
        }
    }
}