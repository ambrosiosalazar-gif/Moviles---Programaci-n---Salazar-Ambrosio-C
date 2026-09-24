package com.salazar.clinicasaludmas.model

// Representa al medico disponible y establece sus atributos
data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val resenas: Int,
    val experienciaAnios: Int,
    val descripcion: String
)
