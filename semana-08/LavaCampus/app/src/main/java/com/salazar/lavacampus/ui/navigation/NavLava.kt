package com.salazar.lavacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.salazar.lavacampus.ui.estudiante.LoginScreen
import com.salazar.lavacampus.ui.estudiante.SplashScreen
import com.salazar.lavacampus.viewmodel.SesionViewModel

@Composable
fun NavLava(
    sesionViewModel: SesionViewModel
) {
    val navController = rememberNavController()

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
                    // Navegación según rol
                }
            )
        }
    }
}
