package com.salazar.clinicasaludmas.model

data class Usuario(
    val id: String,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val dni: String
)