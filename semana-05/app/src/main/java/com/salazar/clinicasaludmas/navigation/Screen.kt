package com.salazar.clinicasaludmas.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Home : Screen("home")
    object Especialidades : Screen("especialidades")

    object Medicos : Screen("medicos/{especialidadId}") {
        fun createRoute(especialidadId: String) = "medicos/$especialidadId"
    }

    object FechaHora : Screen("fechahora/{medicoId}") {
        fun createRoute(medicoId: String) = "fechahora/$medicoId"
    }

    object ConfirmarCita : Screen("confirmar/{medicoId}/{fecha}/{hora}") {
        fun createRoute(medicoId: String, fecha: String, hora: String) = "confirmar/$medicoId/$fecha/$hora"
    }

    object CitaExitosa : Screen("cita_exitosa")
    object MisCitas : Screen("mis_citas")
    object Perfil : Screen("perfil")
    object Resultados : Screen("resultados")
}