package com.ortiz.mytecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(onMenuClick: () -> Unit = {}) {
    var categoriaSel by remember { mutableStateOf("Todos") }
    val lista = if (categoriaSel == "Todos") productos
    else productos.filter { it.categoria == categoriaSel }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TECSUP Store") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { cat ->
                    FilterChip(
                        selected = cat == categoriaSel,
                        onClick = { categoriaSel = cat },
                        label = { Text(cat) }
                    )
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lista) { producto ->
                    TarjetaProducto(producto)
                }
            }
        }
    }
}