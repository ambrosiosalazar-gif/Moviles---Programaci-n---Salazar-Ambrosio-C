import kotlin.system.exitProcess

fun main() {

    // ==================== 1. INPUTS ====================
    println("===== SISTEMA DE MATRÍCULA =====")

    print("Ingrese su nombre: ")
    val nombre = readLine()!!.trim()

    print("Ingrese la cantidad de cursos matriculados: ")
    val cantidadCursos = readLine()!!.trim().toInt()

    print("Ingrese el total de créditos matriculados: ")
    val totalCreditos = readLine()!!.trim().toInt()

    print("Ingrese el valor de cada crédito (S/.): ")
    val valorCredito = readLine()!!.trim().toDouble()

    // ==================== 2. CÁLCULOS ====================

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

    // ==================== 3. RESULTADOS ====================
    println()
    println("========== RESUMEN DE MATRÍCULA ==========")
    println("Nombre: $nombre")
    println("Cursos matriculados: $cantidadCursos")
    println("Total de créditos: $totalCreditos")
    println("Total a pagar: S/. %.2f".format(totalAPagar))
    println("Carga académica: $cargaAcademica")
    println("Forma de pago: $numeroCuotas cuotas de S/. %.2f cada una".format(montoPorCuota))
    println("============================================")
}
