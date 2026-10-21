package com.salazar.clinicasaludmas.model

// Representa la cita agendada por el usuario y su estado: Confirmada o Completada
data class Cita(
    val id: Int,
    val medicoId: Int,
    val medicoNombre: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: String
)
