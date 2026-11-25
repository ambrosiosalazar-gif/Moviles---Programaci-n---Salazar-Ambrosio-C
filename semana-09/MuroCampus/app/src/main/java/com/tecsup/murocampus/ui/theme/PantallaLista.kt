package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    vm: PostsViewModel,
    onPostClick: (Int) -> Unit = {},
    onCrear: () -> Unit = {}
) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val filtro by vm.usuarioFiltro.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MuroCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrear) { Text("+") }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {

            // Fila de chips: Todos, Usuario 1 ... Usuario 10
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = filtro == null,
                        onClick = { vm.filtrar(null) },
                        label = { Text("Todos") }
                    )
                }
                items((1..10).toList()) { u ->
                    FilterChip(
                        selected = filtro == u,
                        onClick = { vm.filtrar(u) },
                        label = { Text("Usuario $u") }
                    )
                }
            }

            // Estados de la lista
            when (val e = estado) {
                is UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorConReintento(
                    mensaje = e.mensaje,
                    onReintentar = { vm.cargar() }
                )
                is UiState.Exito -> {
                    if (e.datos.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Sin publicaciones")
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(e.datos, key = { it.id }) { post ->
                                TarjetaPost(post = post, onClick = { onPostClick(post.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}