import kotlin.system.exitProcess

fun main() {

    // ==================== AFORO (nuevo) ====================
    // La aplicación comienza preguntando el aforo total de la institución
    // (cuántos estudiantes como máximo se pueden matricular) y cuántos
    // estudiantes ya están matriculados hasta el momento.
    println("===== CONTROL DE AFORO =====")
    print("Ingrese el aforo total de la institución: ")
    val aforoTotal = readLine()!!.trim().toInt()

    print("Ingrese la cantidad de estudiantes ya matriculados: ")
    val matriculadosActuales = readLine()!!.trim().toInt()

    if (matriculadosActuales >= aforoTotal) {
        println()
        println("========== AFORO COMPLETO ==========")
        println("No se pueden matricular más estudiantes. El aforo de $aforoTotal ya fue alcanzado.")
        println("=====================================")
        exitProcess(0)
    }

    val cuposDisponibles = aforoTotal - matriculadosActuales
    println("Cupos disponibles: $cuposDisponibles")
    println()

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

    // ==================== TURNO ====================
    print("Ingrese el turno (mañana/tarde/noche): ")
    val turno = readLine()!!.trim().lowercase()

    val porcentajeRecargoTurno: Double
    if (turno == "mañana") {
        porcentajeRecargoTurno = 0.10
    } else if (turno == "tarde") {
        porcentajeRecargoTurno = 0.15
    } else if (turno == "noche") {
        porcentajeRecargoTurno = 0.20
    } else {
        porcentajeRecargoTurno = 0.0
    }

    // ==================== CATEGORÍA ====================
    print("Ingrese la categoría (ordinario/becado): ")
    val categoria = readLine()!!.trim().lowercase()

    val montoMatricula: Double
    if (categoria == "ordinario") {
        print("Ingrese el monto de matrícula a pagar (S/.): ")
        montoMatricula = readLine()!!.trim().toDouble()
    } else {
        montoMatricula = 0.0
    }

    // ==================== 2. CÁLCULOS ====================

    var totalCreditos = 0
    for (i in 0 until cantidadCursos) {
        totalCreditos += creditosCursos[i]
    }

    val totalCursos = totalCreditos * valorCredito

    val recargoTurno = totalCursos * porcentajeRecargoTurno
    val totalConTurno = totalCursos + recargoTurno

    val totalConMatricula = totalConTurno + montoMatricula

    val igv = totalConMatricula * 0.18
    val totalConIGV = totalConMatricula + igv

    val cargaAcademica: String
    if (totalCreditos <= 12) {
        cargaAcademica = "Malla regular"
    } else if (totalCreditos in 13..18) {
        cargaAcademica = "Carga completa"
    } else {
        cargaAcademica = "Requiere autorización"
    }

    val numeroCuotas: Int
    val montoPorCuota: Double
    if (totalConIGV > 1500.0) {
        numeroCuotas = 3
    } else {
        numeroCuotas = 2
    }
    montoPorCuota = totalConIGV / numeroCuotas

    // ==================== 3. RESULTADOS ====================
    println()
    println("========== RESUMEN DE MATRÍCULA ==========")
    println("Nombre: $nombre")
    println("Aforo de la institución: $aforoTotal (cupos disponibles antes de esta matrícula: $cuposDisponibles)")
    println("Cursos matriculados: $cantidadCursos")
    println("Turno: $turno (recargo: ${(porcentajeRecargoTurno * 100).toInt()}%)")
    println("Categoría: $categoria")
    println()

    println("Curso                          Créditos   Costo")
    println("--------------------------------------------------")
    for (i in 0 until cantidadCursos) {
        val costoCurso = creditosCursos[i] * valorCredito
        println("%-30s %-10d S/. %.2f".format(nombresCursos[i], creditosCursos[i], costoCurso))
    }
    println("--------------------------------------------------")

    println()
    println("Total de créditos: $totalCreditos")
    println("Subtotal cursos: S/. %.2f".format(totalCursos))
    println("Recargo por turno: S/. %.2f".format(recargoTurno))
    println("Monto de matrícula: S/. %.2f".format(montoMatricula))
    println("IGV (18%%): S/. %.2f".format(igv))
    println("Total a pagar (con IGV): S/. %.2f".format(totalConIGV))
    println("Carga académica: $cargaAcademica")
    println("Forma de pago: $numeroCuotas cuotas de S/. %.2f cada una".format(montoPorCuota))
    println("============================================")
}
