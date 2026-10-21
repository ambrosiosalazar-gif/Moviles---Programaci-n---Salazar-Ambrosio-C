package com.ortiz.mytecsupstore
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String
)

val categorias = listOf("Todos", "Laptops", "Celulares", "Accesorios")

val productos = listOf(
    Producto(1, "Laptop Lenovo IdeaPad", 2499.0, "Laptops"),
    Producto(2, "Laptop HP Pavilion", 2899.0, "Laptops"),
    Producto(3, "Samsung Galaxy A15", 799.0, "Celulares"),
    Producto(4, "iPhone 13", 2999.0, "Celulares"),
    Producto(5, "Mouse inalámbrico", 59.0, "Accesorios"),
    Producto(6, "Audífonos Bluetooth", 129.0, "Accesorios")
)

