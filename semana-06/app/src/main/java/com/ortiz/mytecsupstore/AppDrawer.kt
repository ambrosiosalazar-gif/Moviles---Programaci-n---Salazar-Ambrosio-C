package com.ortiz.mytecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class DestinoDrawer(val titulo: String, val icono: ImageVector)

val destinosDrawer = listOf(
    DestinoDrawer("Inicio", Icons.Default.Home),
    DestinoDrawer("Mis pedidos", Icons.Default.ShoppingCart),
    DestinoDrawer("Favoritos", Icons.Default.Favorite),
    DestinoDrawer("Perfil", Icons.Default.Person),
    DestinoDrawer("Cerrar sesión", Icons.AutoMirrored.Filled.ExitToApp)
)

@Composable
fun AppDrawer(
    destinoActual: String,
    favoritosCount: Int = 0,
    onDestinoClick: (String) -> Unit
) {
    ModalDrawerSheet(drawerContainerColor = Color.White) {
        // Encabezado de usuario
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = LavandaClaro,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "AS",
                        color = Morado,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    "Ambrosio Salazar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "ambrosio@tecsup.edu.pe",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(modifier = Modifier.height(12.dp))

        // Destinos
        destinosDrawer.forEach { destino ->
            val activo = destino.titulo == destinoActual
            NavigationDrawerItem(
                label = {
                    Text(
                        destino.titulo,
                        fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(destino.icono, contentDescription = null) },
                badge = {
                    if (destino.titulo == "Favoritos" && favoritosCount > 0) {
                        Badge(
                            containerColor = Morado,
                            contentColor = Color.White
                        ) {
                            Text(favoritosCount.toString())
                        }
                    }
                },
                selected = activo,
                onClick = { onDestinoClick(destino.titulo) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = LavandaClaro,
                    selectedTextColor = Morado,
                    selectedIconColor = Morado,
                    unselectedContainerColor = Color.Transparent
                ),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
            )
        }
    }
}