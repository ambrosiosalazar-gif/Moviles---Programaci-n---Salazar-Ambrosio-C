package com.ortiz.mytecsupstore

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class DestinoDrawer(val titulo: String, val icono: ImageVector)

val destinosDrawer = listOf(
    DestinoDrawer("Inicio", Icons.Default.Home),
    DestinoDrawer("Mis pedidos", Icons.Default.ShoppingCart),
    DestinoDrawer("Favoritos", Icons.Default.Favorite),
    DestinoDrawer("Perfil", Icons.Default.Person)
)

@Composable
fun AppDrawer(
    destinoActual: String,
    onDestinoClick: (String) -> Unit
) {
    ModalDrawerSheet {
        Text(
            "TECSUP Store",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider()
        destinosDrawer.forEach { destino ->
            NavigationDrawerItem(
                label = { Text(destino.titulo) },
                icon = { Icon(destino.icono, contentDescription = null) },
                selected = destino.titulo == destinoActual,
                onClick = { onDestinoClick(destino.titulo) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}