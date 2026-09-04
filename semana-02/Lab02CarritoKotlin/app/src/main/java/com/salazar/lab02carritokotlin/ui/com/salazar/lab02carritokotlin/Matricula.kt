import kotlin.system.exitProcess

fun main() {

    // ==================== 1. INPUTS ====================
    println("===== SISTEMA DE MATRÍCULA =====")

    print("Ingrese su nombre: ")
    val nombre = readLine()!!.trim()

    print("Ingrese la cantidad de cursos matriculados: ")
    val cantidadCursos = readLine()!!.trim().toInt()

    print("Ingrese el valor de cada crédito (S/.): ")
    val valorCredito = readLine()!!.trim().toDouble()

    val nombresCursos = arrayOfNulls<String>(cantidadCursos)
    val creditosCursos = IntArray(cantidadCursos)
}