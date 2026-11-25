package com.salazar.clinicasaludmas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.clinicasaludmas.navigation.Screen

@Composable
fun AppDrawer(
    usuarioNombre: String,
    onNavigate: (String) -> Unit,
    onCerrarSesion: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(drawerContainerColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // ENCABEZADO DE USUARIO CON AVATAR
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = usuarioNombre.take(2).uppercase(),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(usuarioNombre, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Text("Paciente Asegurado", fontSize = 12.sp, color = Color.Gray)
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            "Plan SaludPlus",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            // MENÚ DE NAVEGACIÓN PRINCIPAL
            NavigationDrawerItem(
                label = { Text("Inicio / Dashboard", fontWeight = FontWeight.SemiBold) },
                icon = { Icon(Icons.Default.Home, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                selected = false,
                onClick = { onNavigate(Screen.Home.route); onCloseDrawer() }
            )

            NavigationDrawerItem(
                label = { Text("Nuestras Sedes", fontWeight = FontWeight.SemiBold) },
                icon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                selected = false,
                onClick = { onNavigate(Screen.Sedes.route); onCloseDrawer() }
            )

            NavigationDrawerItem(
                label = { Text("Doctores y Especialidades", fontWeight = FontWeight.SemiBold) },
                icon = { Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                selected = false,
                onClick = { onNavigate(Screen.Especialidades.route); onCloseDrawer() }
            )

            NavigationDrawerItem(
                label = { Text("Mi Agenda / Citas", fontWeight = FontWeight.SemiBold) },
                icon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                selected = false,
                onClick = { onNavigate(Screen.MisCitas.route); onCloseDrawer() }
            )

            NavigationDrawerItem(
                label = { Text("Historial Médico / Resultados", fontWeight = FontWeight.SemiBold) },
                icon = { Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                selected = false,
                onClick = { onNavigate(Screen.Resultados.route); onCloseDrawer() }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onCerrarSesion(); onCloseDrawer() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
            }
        }
    }
}