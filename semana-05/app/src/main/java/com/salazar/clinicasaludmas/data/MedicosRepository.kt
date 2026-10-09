package com.salazar.clinicasaludmas.data

import com.salazar.clinicasaludmas.model.*

object MedicosRepository {
    val usuarios = mutableListOf<Usuario>()
    val especialidades = mutableListOf<Especialidad>()
    val medicos = mutableListOf<Medico>()
    val citas = mutableListOf<Cita>()

    var usuarioActual: Usuario? = null

    init {
        // Usuario inicial de prueba
        usuarios.add(Usuario("1", "Juan Perez", "juan@correo.com", "123456", "77889900"))
        usuarioActual = usuarios.first()

        // Especialidades de prueba
        especialidades.addAll(
            listOf(
                Especialidad("1", "Medicina General", "Atención médica integral primaria"),
                Especialidad("2", "Pediatría", "Cuidado y atención infantil"),
                Especialidad("3", "Odontología", "Salud bucal y tratamiento dental"),
                Especialidad("4", "Cardiología", "Salud cardiovascular"),
                Especialidad("5", "Ginecología", "Salud integral de la mujer"),
                Especialidad("6", "Traumatología", "Huesos, articulaciones y lesiones")
            )
        )

        // Médicos de prueba
        medicos.addAll(
            listOf(
                Medico("101", "Dra. Ana Torres", "1", "Medicina General", "45678", 4.8),
                Medico("102", "Dra. Claudia Rojas", "1", "Medicina General", "45679", 4.7),
                Medico("103", "Dr. Luis Forno", "2", "Pediatría", "45680", 4.9),
                Medico("104", "Dra. Xiomara Soto", "3", "Odontología", "45681", 4.6)
            )
        )
    }

    // --- Funciones del PDF ---
    fun registrarUsuario(usuario: Usuario): Boolean {
        if (usuarios.any { it.correo == usuario.correo }) return false
        usuarios.add(usuario)
        usuarioActual = usuario
        return true
    }

    fun iniciarSesion(correo: String, contrasena: String): Boolean {
        val user = usuarios.find { it.correo == correo && it.contrasena == contrasena }
        if (user != null) {
            usuarioActual = user
            return true
        }
        return false
    }

    fun cerrarSesion() {
        usuarioActual = null
    }

    fun buscarEspecialidades(query: String): List<Especialidad> {
        if (query.isEmpty()) return especialidades
        return especialidades.filter { it.nombre.contains(query, ignoreCase = true) }
    }

    fun especialidadesDestacadas(): List<Especialidad> {
        return especialidades.take(3)
    }

    fun medicosPorEspecialidad(especialidadId: String): List<Medico> {
        return medicos.filter { it.especialidadId == especialidadId }.sortedByDescending { it.calificacion }
    }

    fun obtenerMedico(id: String): Medico? = medicos.find { it.id == id }

    fun obtenerEspecialidad(id: String): Especialidad? = especialidades.find { it.id == id }

    fun horariosDisponibles(medicoId: String, fecha: String): List<String> {
        val horariosBase = listOf("08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM")
        val ocupados = citas.filter { it.medicoId == medicoId && it.fecha == fecha }.map { it.hora }
        return horariosBase.filter { !ocupados.contains(it) }
    }

    fun agendarCita(cita: Cita) {
        citas.add(cita)
    }

    fun citasDelUsuario(): List<Cita> {
        return citas.filter { it.usuarioId == usuarioActual?.id }
    }
}