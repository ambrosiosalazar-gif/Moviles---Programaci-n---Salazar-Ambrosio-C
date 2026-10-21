package com.ortiz.mytecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
        // Encabezado de usuario
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "AS",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Ambrosio Salazar", style = MaterialTheme.typography.titleMedium)
                Text("ambrosio@tecsup.edu.pe", style = MaterialTheme.typography.bodySmall)
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        // Destinos
        destinosDrawer.forEach { destino ->
            NavigationDrawerItem(
                label = { Text(destino.titulo) },
                icon = { Icon(destino.icono, contentDescription = null) },
                selected = destino.titulo == destinoActual,
                onClick = { onDestinoClick(destino.titulo) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}