package com.salazar.lab02carritokotlin

abstract class Producto(
    nombre: String,
    precio: Double,
    cantidad: Int
) {
    private var _nombre: String = nombre
    private var _precio: Double = precio
    private var _cantidad: Int = cantidad

    val nombre: String
        get() = _nombre

    val precio: Double
        get() = _precio

    var cantidad: Int
        get() = _cantidad
        set(value) {
            if (value > 0) {
                _cantidad = value
            } else {
                println("Cantidad inválida, debe ser mayor a 0")
            }
        }

    fun calcularImporte(): Double {
        return precio * cantidad
    }

    abstract fun calcularImpuesto(): Double


    abstract fun categoria(): String
}

class Electronico(nombre: String, precio: Double, cantidad: Int) : Producto(nombre, precio, cantidad) {
    override fun calcularImpuesto(): Double {
        return calcularImporte() * 0.18
    }

    override fun categoria(): String = "Electrónico"
}

class Alimento(nombre: String, precio: Double, cantidad: Int) : Producto(nombre, precio, cantidad) {
    override fun calcularImpuesto(): Double {
        return 0.0
    }

    override fun categoria(): String = "Alimento"
}

class Ropa(nombre: String, precio: Double, cantidad: Int) : Producto(nombre, precio, cantidad) {
    override fun calcularImpuesto(): Double {
        return calcularImporte() * 0.18
    }

    override fun categoria(): String = "Ropa"
}