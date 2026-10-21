package com.salazar.lavacampus.model

data class ClienteResumen(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val pedidos: Int,
    val gastado: Double
)
