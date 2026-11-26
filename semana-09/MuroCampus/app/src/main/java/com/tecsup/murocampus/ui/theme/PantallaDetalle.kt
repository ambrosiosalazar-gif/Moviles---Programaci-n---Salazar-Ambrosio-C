package com.tecsup.murocampus.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    vm: PostsViewModel,
    id: Int,
    onAtras: () -> Unit
) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    val borrado by vm.borrado.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        vm.limpiarBorrado()
        vm.abrir(id)
    }

    // Si se borró con éxito, volvemos a la lista
    LaunchedEffect(borrado) {
        if (borrado is UiState.Exito) {
            vm.limpiarBorrado()
            onAtras()
        }
    }

    BackHandler(onBack = onAtras)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicación") },
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
        when (val e = estado) {
            is UiState.Cargando -> Cargando(Modifier.padding(padding))
            is UiState.Error -> ErrorConReintento(
                mensaje = e.mensaje,
                onReintentar = { vm.abrir(id) },
                modifier = Modifier.padding(padding)
            )
            is UiState.Exito -> {
                val d = e.datos
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Column {
                            Text(
                                d.post.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                d.post.body,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            OutlinedButton(
                                onClick = { vm.borrar(id) },
                                enabled = borrado !is UiState.Cargando,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.padding(top = 12.dp)
                            ) { Text("Eliminar") }

                            (borrado as? UiState.Error)?.let {
                                Text(
                                    it.mensaje,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                            Text(
                                "Comentarios (${d.comentarios.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    items(d.comentarios, key = { it.id }) { c ->
                        Card {
                            Column(Modifier.padding(12.dp)) {
                                Text(
                                    c.email,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    c.body,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}