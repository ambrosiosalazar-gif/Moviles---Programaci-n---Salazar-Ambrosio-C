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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    onMenuClick: () -> Unit = {},
    favoritos: List<Int> = emptyList(),
    onToggleFavorito: (Int) -> Unit = {}
) {
    var categoriaSel by remember { mutableStateOf("Todos") }
    val lista = if (categoriaSel == "Todos") productos
    else productos.filter { it.categoria == categoriaSel }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("TECSUP Store", fontWeight = FontWeight.Bold)
                        Text("Más vendidos", style = MaterialTheme.typography.bodySmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Morado,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
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
                    TarjetaProducto(
                        producto = producto,
                        esFavorito = producto.id in favoritos,
                        onFavoritoClick = { onToggleFavorito(producto.id) }
                    )
                }
            }
        }
    }
}