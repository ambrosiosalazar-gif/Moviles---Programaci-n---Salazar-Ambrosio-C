package com.salazar.clinicasaludmas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salazar.clinicasaludmas.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Registro.route) { RegistroScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Especialidades.route) { EspecialidadesScreen(navController) }

        composable(
            route = Screen.Medicos.route,
            arguments = listOf(navArgument("especialidadId") { type = NavType.StringType })
        ) { backStackEntry ->
            val especId = backStackEntry.arguments?.getString("especialidadId") ?: ""
            DoctorProfileScreen(navController, especId)
        }

        composable(
            route = Screen.FechaHora.route,
            arguments = listOf(navArgument("medicoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getString("medicoId") ?: ""
            BookAppointmentScreen(navController, medicoId)
        }

        composable(
            route = Screen.ConfirmarCita.route,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.StringType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getString("medicoId") ?: ""
            val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
            val hora = backStackEntry.arguments?.getString("hora") ?: ""
            ConfirmationScreen(navController, medicoId, fecha, hora)
        }

        composable(Screen.CitaExitosa.route) { CitaExitosaScreen(navController) }
        composable(Screen.MisCitas.route) { MisCitasScreen(navController) }
        composable(Screen.Perfil.route) { PerfilScreen(navController) }
        composable(Screen.Resultados.route) { HistorialMedicoScreen(navController) }
    }
}