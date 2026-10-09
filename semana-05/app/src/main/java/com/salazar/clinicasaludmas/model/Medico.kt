package com.salazar.clinicasaludmas.model

data class Medico(
    val id: String,
    val nombre: String,
    val especialidadId: String,
    val especialidadNombre: String,
    val CMP: String,
    val calificacion: Double,
    val fotoResId: Int = 0
)