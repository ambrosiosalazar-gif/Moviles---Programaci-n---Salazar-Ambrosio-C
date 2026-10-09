package com.salazar.clinicasaludmas.model

data class Cita(
    val id: String,
    val usuarioId: String,
    val medicoId: String,
    val nombreMedico: String,
    val especialidadNombre: String,
    val fecha: String,
    val hora: String,
    val estado: String = "Confirmada"
)