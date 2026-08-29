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

    // ==================== CICLO REPETITIVO ====================
    for (i in 0 until cantidadCursos) {
        println()
        println("--- Curso ${i + 1} ---")
        print("Nombre del curso: ")
        nombresCursos[i] = readLine()!!.trim()

        print("Créditos del curso: ")
        creditosCursos[i] = readLine()!!.trim().toInt()
    }

    // ==================== 2. CÁLCULOS ====================

    // Total de créditos: suma de los créditos de todos los cursos
    var totalCreditos = 0
    for (i in 0 until cantidadCursos) {
        totalCreditos += creditosCursos[i]
    }

    // Costo total
    val totalAPagar = totalCreditos * valorCredito

    // Carga académica según el total de créditos
    val cargaAcademica: String
    if (totalCreditos <= 12) {
        cargaAcademica = "Malla regular"
    } else if (totalCreditos in 13..18) {
        cargaAcademica = "Carga completa"
    } else {
        cargaAcademica = "Requiere autorización"
    }

    // Forma de pago según el total a pagar
    val numeroCuotas: Int
    val montoPorCuota: Double
    if (totalAPagar > 1500.0) {
        numeroCuotas = 3
    } else {
        numeroCuotas = 2
    }
    montoPorCuota = totalAPagar / numeroCuotas
}