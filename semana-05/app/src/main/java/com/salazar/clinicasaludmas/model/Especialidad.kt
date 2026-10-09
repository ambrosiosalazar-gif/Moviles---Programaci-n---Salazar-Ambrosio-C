package com.salazar.clinicasaludmas.model

data class Especialidad(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val iconoResId: Int = 0
)