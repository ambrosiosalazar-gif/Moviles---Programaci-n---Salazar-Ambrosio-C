package com.ortiz.mytecsupstore

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val favoritos = remember { mutableStateListOf<Int>() }

    val entradaActual by navController.currentBackStackEntryAsState()
    val destinoActual = when (entradaActual?.destination?.route) {
        "pedidos" -> "Mis pedidos"
        "favoritos" -> "Favoritos"
        "perfil" -> "Perfil"
        else -> "Inicio"
    }

    val abrirMenu: () -> Unit = { scope.launch { drawerState.open() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                destinoActual = destinoActual,
                onDestinoClick = { destino ->
                    scope.launch { drawerState.close() }
                    val ruta = when (destino) {
                        "Mis pedidos" -> "pedidos"
                        "Favoritos" -> "favoritos"
                        "Perfil" -> "perfil"
                        else -> "inicio"
                    }
                    navController.navigate(ruta) {
                        popUpTo("inicio")
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") {
                PantallaInicio(
                    onMenuClick = abrirMenu,
                    favoritos = favoritos,
                    onToggleFavorito = { id ->
                        if (id in favoritos) favoritos.remove(id) else favoritos.add(id)
                    }
                )
            }
            composable("pedidos") { PantallaSimple("Mis pedidos", abrirMenu) }
            composable("favoritos") { PantallaSimple("Favoritos", abrirMenu) }
            composable("perfil") { PantallaSimple("Perfil", abrirMenu) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSimple(titulo: String, onMenuClick: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(titulo, style = MaterialTheme.typography.headlineMedium)
        }
    }
}