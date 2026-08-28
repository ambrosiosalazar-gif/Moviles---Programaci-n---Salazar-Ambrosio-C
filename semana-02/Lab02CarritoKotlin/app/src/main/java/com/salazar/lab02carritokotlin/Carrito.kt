package com.salazar.lab02carritokotlin

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    print("Ingrese el nombre del cliente: ")
    val nombreCliente = readLine() ?: "Cliente"

    val carrito = CarritoCompras(nombreCliente)

    print("¿Cuántos productos desea ingresar? ")
    val cantidadProductos = readLine()?.toIntOrNull() ?: 0

    for (i in 1..cantidadProductos) {
        println()
        println("--- Producto $i ---")

        print("Nombre del producto: ")
        val nombre = readLine() ?: "Producto sin nombre"

        print("Precio: ")
        val precio = readLine()?.toDoubleOrNull() ?: 0.0

        print("Cantidad: ")
        val cantidad = readLine()?.toIntOrNull() ?: 1

        print("Categoría (1=Electronico, 2=Alimento, 3=Ropa): ")
        val opcionCategoria = readLine()?.toIntOrNull() ?: 1

        val producto: Producto = when (opcionCategoria) {
            2 -> Alimento(nombre, precio, cantidad)
            3 -> Ropa(nombre, precio, cantidad)
            else -> Electronico(nombre, precio, cantidad)
        }

        carrito.agregarProducto(producto)
    }

    carrito.mostrarResumen()
}