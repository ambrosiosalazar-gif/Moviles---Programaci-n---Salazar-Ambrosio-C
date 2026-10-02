package com.ortiz.mytecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaProducto(producto: Producto, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text("S/ ${producto.precio}", style = MaterialTheme.typography.bodyMedium)
            }
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Favoritos") },
                        leadingIcon = {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                        },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Compartir") },
                        leadingIcon = {
                            Icon(Icons.Default.Share, contentDescription = null)
                        },
                        onClick = { expanded = false }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Reportar") },
                        leadingIcon = {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        },
                        onClick = { expanded = false }
                    )
                }
            }
        }
    }
}