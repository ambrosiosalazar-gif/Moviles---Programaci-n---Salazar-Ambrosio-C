package com.salazar.lavacampus.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.salazar.lavacampus.data.prefs.PreferenciasStore
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.ui.administrador.AjustesScreen
import com.salazar.lavacampus.ui.administrador.BandejaScreen
import com.salazar.lavacampus.ui.administrador.ClientesScreen
import com.salazar.lavacampus.ui.administrador.FormularioServicioScreen
import com.salazar.lavacampus.ui.administrador.GestionPedidoScreen
import com.salazar.lavacampus.ui.administrador.PanelScreen
import com.salazar.lavacampus.ui.administrador.ServiciosScreen
import com.salazar.lavacampus.ui.estudiante.DetallePedidoScreen
import com.salazar.lavacampus.ui.estudiante.InicioScreen
import com.salazar.lavacampus.ui.estudiante.LoginScreen
import com.salazar.lavacampus.ui.estudiante.MisPedidosScreen
import com.salazar.lavacampus.ui.estudiante.NuevoPedidoScreen
import com.salazar.lavacampus.ui.estudiante.PerfilScreen
import com.salazar.lavacampus.ui.estudiante.SplashScreen
import com.salazar.lavacampus.ui.estudiante.TarifasScreen
import com.salazar.lavacampus.viewmodel.BandejaViewModel
import com.salazar.lavacampus.viewmodel.ClientesViewModel
import com.salazar.lavacampus.viewmodel.DetallePedidoViewModel
import com.salazar.lavacampus.viewmodel.GestionPedidoViewModel
import com.salazar.lavacampus.viewmodel.InicioViewModel
import com.salazar.lavacampus.viewmodel.LavaViewModelFactory
import com.salazar.lavacampus.viewmodel.MisPedidosViewModel
import com.salazar.lavacampus.viewmodel.NuevoPedidoViewModel
import com.salazar.lavacampus.viewmodel.PanelViewModel
import com.salazar.lavacampus.viewmodel.ServiciosViewModel
import com.salazar.lavacampus.viewmodel.SesionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NavLava(
    repository: LavaRepository,
    prefsStore: PreferenciasStore,
    sesionViewModel: SesionViewModel
) {
    val navController = rememberNavController()
    val usuarioId by sesionViewModel.usuarioId.collectAsState(initial = 0)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val studentRoutes = listOf(Rutas.InicioEstudiante.ruta, Rutas.MisPedidos.ruta, Rutas.Tarifas.ruta, Rutas.PerfilEstudiante.ruta)
    val adminRoutes = listOf(Rutas.PanelAdmin.ruta, Rutas.BandejaAdmin.ruta, Rutas.ServiciosAdmin.ruta, Rutas.ClientesAdmin.ruta, Rutas.AjustesAdmin.ruta)

    Scaffold(
        bottomBar = {
            if (currentRoute in studentRoutes) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio") },
                        selected = currentRoute == Rutas.InicioEstudiante.ruta,
                        onClick = { navController.navigate(Rutas.InicioEstudiante.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Pedidos") },
                        label = { Text("Pedidos") },
                        selected = currentRoute == Rutas.MisPedidos.ruta,
                        onClick = { navController.navigate(Rutas.MisPedidos.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Info, contentDescription = "Tarifas") },
                        label = { Text("Tarifas") },
                        selected = currentRoute == Rutas.Tarifas.ruta,
                        onClick = { navController.navigate(Rutas.Tarifas.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                        label = { Text("Perfil") },
                        selected = currentRoute == Rutas.PerfilEstudiante.ruta,
                        onClick = { navController.navigate(Rutas.PerfilEstudiante.ruta) }
                    )
                }
            } else if (currentRoute in adminRoutes) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Panel") },
                        label = { Text("Panel") },
                        selected = currentRoute == Rutas.PanelAdmin.ruta,
                        onClick = { navController.navigate(Rutas.PanelAdmin.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Bandeja") },
                        label = { Text("Bandeja") },
                        selected = currentRoute == Rutas.BandejaAdmin.ruta,
                        onClick = { navController.navigate(Rutas.BandejaAdmin.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Store, contentDescription = "Servicios") },
                        label = { Text("Servicios") },
                        selected = currentRoute == Rutas.ServiciosAdmin.ruta,
                        onClick = { navController.navigate(Rutas.ServiciosAdmin.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.People, contentDescription = "Clientes") },
                        label = { Text("Clientes") },
                        selected = currentRoute == Rutas.ClientesAdmin.ruta,
                        onClick = { navController.navigate(Rutas.ClientesAdmin.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                        label = { Text("Ajustes") },
                        selected = currentRoute == Rutas.AjustesAdmin.ruta,
                        onClick = { navController.navigate(Rutas.AjustesAdmin.ruta) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.Splash.ruta,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Rutas.Splash.ruta) {
                SplashScreen(
                    onFinish = {
                        navController.navigate(Rutas.Login.ruta) {
                            popUpTo(Rutas.Splash.ruta) { inclusive = true }
                        }
                    }
                )
            }
            composable(Rutas.Login.ruta) {
                LoginScreen(
                    vm = sesionViewModel,
                    onLoginExitoso = { rol ->
                        if (rol.name == "CLIENTE") {
                            navController.navigate(Rutas.InicioEstudiante.ruta) {
                                popUpTo(Rutas.Login.ruta) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Rutas.PanelAdmin.ruta) {
                                popUpTo(Rutas.Login.ruta) { inclusive = true }
                            }
                        }
                    }
                )
            }
            // Estudiante
            composable(Rutas.InicioEstudiante.ruta) {
                val inicioVm: InicioViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore, usuarioId))
                InicioScreen(
                    vm = inicioVm,
                    onNuevoPedido = { navController.navigate(Rutas.NuevoPedido.ruta) },
                    onDetallePedido = { id -> navController.navigate(Rutas.DetallePedido.crearRuta(id)) }
                )
            }
            composable(Rutas.MisPedidos.ruta) {
                val misPedidosVm: MisPedidosViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore, usuarioId))
                MisPedidosScreen(
                    vm = misPedidosVm,
                    onDetallePedido = { id -> navController.navigate(Rutas.DetallePedido.crearRuta(id)) },
                    onNuevoPedido = { navController.navigate(Rutas.NuevoPedido.ruta) }
                )
            }
            composable(Rutas.Tarifas.ruta) {
                val serviciosVm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore))
                TarifasScreen(vm = serviciosVm)
            }
            composable(Rutas.PerfilEstudiante.ruta) {
                PerfilScreen(
                    onCerrarSesion = {
                        CoroutineScope(Dispatchers.IO).launch { prefsStore.cerrarSesion() }
                        navController.navigate(Rutas.Login.ruta) { popUpTo(0) { inclusive = true } }
                    },
                    onCambiarPerfil = {
                        navController.navigate(Rutas.Login.ruta) { popUpTo(0) { inclusive = true } }
                    }
                )
            }
            composable(Rutas.NuevoPedido.ruta) {
                val nuevoVm: NuevoPedidoViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore, usuarioId))
                NuevoPedidoScreen(
                    vm = nuevoVm,
                    onVolver = { navController.popBackStack() },
                    onPedidoCreado = { navController.popBackStack() }
                )
            }
            composable(Rutas.DetallePedido.ruta) { backStackEntry ->
                val pedidoId = backStackEntry.arguments?.getString("pedidoId")?.toIntOrNull() ?: 0
                val detalleVm: DetallePedidoViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore, pedidoId))
                DetallePedidoScreen(
                    vm = detalleVm,
                    onVolver = { navController.popBackStack() }
                )
            }

            // Administrador
            composable(Rutas.PanelAdmin.ruta) {
                val panelVm: PanelViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore))
                PanelScreen(
                    vm = panelVm,
                    onVerBandeja = { navController.navigate(Rutas.BandejaAdmin.ruta) }
                )
            }
            composable(Rutas.BandejaAdmin.ruta) {
                val bandejaVm: BandejaViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore))
                BandejaScreen(
                    vm = bandejaVm,
                    onGestionarPedido = { id -> navController.navigate(Rutas.GestionPedido.crearRuta(id)) }
                )
            }
            composable(Rutas.GestionPedido.ruta) { backStackEntry ->
                val pedidoId = backStackEntry.arguments?.getString("pedidoId")?.toIntOrNull() ?: 0
                val gestionVm: GestionPedidoViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore, pedidoId))
                GestionPedidoScreen(
                    vm = gestionVm,
                    onVolver = { navController.popBackStack() }
                )
            }
            composable(Rutas.ServiciosAdmin.ruta) {
                val serviciosVm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore))
                ServiciosScreen(
                    vm = serviciosVm,
                    onEditar = { id -> navController.navigate(Rutas.FormularioServicio.crearRuta(id)) }
                )
            }
            composable(Rutas.FormularioServicio.ruta) { backStackEntry ->
                val servicioId = backStackEntry.arguments?.getString("servicioId")?.toIntOrNull()
                val serviciosVm: ServiciosViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore))
                FormularioServicioScreen(
                    vm = serviciosVm,
                    servicioId = servicioId,
                    onVolver = { navController.popBackStack() }
                )
            }
            composable(Rutas.ClientesAdmin.ruta) {
                val clientesVm: ClientesViewModel = viewModel(factory = LavaViewModelFactory(repository, prefsStore))
                ClientesScreen(vm = clientesVm)
            }
            composable(Rutas.AjustesAdmin.ruta) {
                AjustesScreen(
                    onCerrarSesion = {
                        CoroutineScope(Dispatchers.IO).launch { prefsStore.cerrarSesion() }
                        navController.navigate(Rutas.Login.ruta) { popUpTo(0) { inclusive = true } }
                    },
                    onCambiarPerfil = {
                        navController.navigate(Rutas.Login.ruta) { popUpTo(0) { inclusive = true } }
                    }
                )
            }
        }
    }
}
