package com.salazar.lavacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.salazar.lavacampus.data.prefs.PreferenciasStore
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.ui.estudiante.InicioScreen
import com.salazar.lavacampus.ui.estudiante.LoginScreen
import com.salazar.lavacampus.ui.estudiante.NuevoPedidoScreen
import com.salazar.lavacampus.ui.estudiante.SplashScreen
import com.salazar.lavacampus.viewmodel.InicioViewModel
import com.salazar.lavacampus.viewmodel.LavaViewModelFactory
import com.salazar.lavacampus.viewmodel.NuevoPedidoViewModel
import com.salazar.lavacampus.viewmodel.SesionViewModel

@Composable
fun NavLava(
    repository: LavaRepository,
    prefsStore: PreferenciasStore,
    sesionViewModel: SesionViewModel
) {
    val navController = rememberNavController()
    val usuarioId by sesionViewModel.usuarioId.collectAsState(initial = 0)

    NavHost(
        navController = navController,
        startDestination = Rutas.Splash.ruta
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
                    }
                }
            )
        }
        composable(Rutas.InicioEstudiante.ruta) {
            val inicioVm: InicioViewModel = viewModel(
                factory = LavaViewModelFactory(repository, prefsStore, usuarioId)
            )
            InicioScreen(
                vm = inicioVm,
                onNuevoPedido = { navController.navigate(Rutas.NuevoPedido.ruta) },
                onDetallePedido = {}
            )
        }
        composable(Rutas.NuevoPedido.ruta) {
            val nuevoVm: NuevoPedidoViewModel = viewModel(
                factory = LavaViewModelFactory(repository, prefsStore, usuarioId)
            )
            NuevoPedidoScreen(
                vm = nuevoVm,
                onVolver = { navController.popBackStack() },
                onPedidoCreado = { navController.popBackStack() }
            )
        }
    }
}
