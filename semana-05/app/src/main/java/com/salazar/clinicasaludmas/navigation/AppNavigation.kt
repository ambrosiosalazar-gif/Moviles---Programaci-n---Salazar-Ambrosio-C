package com.salazar.clinicasaludmas.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.salazar.clinicasaludmas.components.AppDrawer
import com.salazar.clinicasaludmas.model.Cita
import com.salazar.clinicasaludmas.screens.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // el val citas sirve como el parametro que se pasara a cada pantalla que lo necesite
    val citas = remember { mutableStateListOf<Cita>() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Ruta actual usada por AppDrawer
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Aqui el drawer envuelve a todo el navHost para que este disponible en cualquier destino
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                onDestinationClick = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                },
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen(navController, drawerState, scope)
            }
            // El resto de pantallas se agregan en los siguientes commits
        }
    }
}
