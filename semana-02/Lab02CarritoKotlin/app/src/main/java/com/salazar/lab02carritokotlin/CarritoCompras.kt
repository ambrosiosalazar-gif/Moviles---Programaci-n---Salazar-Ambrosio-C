package com.salazar.lab02carritokotlin


class CarritoCompras(private val nombreCliente: String) {

    private val productos = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        productos.add(producto)
        println("Producto agregado: ${producto.nombre}")
    }

    fun cantidadProductos(): Int = productos.size

    fun calcularSubtotal(): Double {
        var subtotal = 0.0
        for (p in productos) {
            subtotal += p.calcularImporte()
        }
        return subtotal
    }

    fun calcularImpuestos(): Double {
        var impuestos = 0.0
        for (p in productos) {
            impuestos += p.calcularImpuesto()
        }
        return impuestos
    }

    fun calcularTotal(): Double {
        return calcularSubtotal() + calcularImpuestos()
    }

    fun calcularDescuento(total: Double): Double {
        return when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
    }

    fun productoMasCaro(): Producto? {
        return productos.maxByOrNull { it.precio }
    }

    fun mostrarDetalle() {
        println("--------- DETALLE DEL CARRITO ---------")
        var i = 1
        for (p in productos) {
            println(String.format("%d. [%s] %-18s x%d S/ %8.2f",
                i, p.categoria(), p.nombre, p.cantidad, p.calcularImporte()))
            i++
        }
        println("---------------------------------------")
    }

    fun mostrarResumen() {
        println()
        println("Cliente: $nombreCliente")
        println()
        mostrarDetalle()
        println("Cantidad de productos: ${cantidadProductos()}")

        val masCaro = productoMasCaro()
        if (masCaro != null) {
            println("Producto mas caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio))
        }

        val subtotal = calcularSubtotal()
        val impuestos = calcularImpuestos()
        val total = calcularTotal()
        val descuento = calcularDescuento(total)
        val totalConDescuento = total - descuento

        println()
        println(String.format("%-15s S/ %8.2f", "Subtotal:", subtotal))
        println(String.format("%-15s S/ %8.2f", "Impuestos:", impuestos))
        println(String.format("%-15s S/ %8.2f", "TOTAL A PAGAR:", total))

        if (descuento > 0) {
            println(String.format("%-15s S/ %8.2f", "Descuento:", descuento))
            println(String.format("%-15s S/ %8.2f", "TOTAL C/DESC:", totalConDescuento))
        } else {
            println("No se aplicó descuento (total no supera S/ 3000)")
        }
    }
}