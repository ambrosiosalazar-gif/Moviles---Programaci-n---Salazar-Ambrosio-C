package com.salazar.lavacampus.ai

import java.text.Normalizer

object AnalizadorNotas {

    private fun normalizar(texto: String): String {
        return try {
            val temp = Normalizer.normalize(texto.lowercase(), Normalizer.Form.NFD)
            java.util.regex.Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(temp).replaceAll("")
        } catch (e: Exception) {
            texto.lowercase()
        }
    }

    fun extraerEtiquetas(nota: String?): List<String> {
        if (nota.isNullOrBlank()) return emptyList()
        return try {
            val textoNorm = normalizar(nota)
            val etiquetas = mutableSetOf<String>()

            val esUrgente = (textoNorm.contains("urgente") || textoNorm.contains("rapido") || textoNorm.contains("para hoy"))
            val negUrgente = (textoNorm.contains("no urgente") || textoNorm.contains("no es urgente") || textoNorm.contains("sin prisa"))
            if (esUrgente && !negUrgente) {
                etiquetas.add("Urgente")
            }

            val esDelicado = (textoNorm.contains("delicad") || textoNorm.contains("suave") || textoNorm.contains("cuidado"))
            val negDelicado = (textoNorm.contains("no delicad") || textoNorm.contains("no es delicad"))
            if (esDelicado && !negDelicado) {
                etiquetas.add("Ropa delicada")
            }

            val esFria = (textoNorm.contains("fria") || textoNorm.contains("frio") || textoNorm.contains("agua fria"))
            val negFria = (textoNorm.contains("no fria") || textoNorm.contains("agua caliente") || textoNorm.contains("no agua fria"))
            if (esFria && !negFria) {
                etiquetas.add("Agua fría")
            }

            val noSuavizante = (textoNorm.contains("sin suavizante") || textoNorm.contains("no suavizante") || textoNorm.contains("evitar suavizante"))
            if (noSuavizante) {
                etiquetas.add("No suavizante")
            }

            val noLejia = (textoNorm.contains("sin lejia") || textoNorm.contains("no lejia") || textoNorm.contains("evitar lejia"))
            if (noLejia) {
                etiquetas.add("No lejía")
            }

            val planchadoEsp = (textoNorm.contains("planchado especial") || textoNorm.contains("bien doblad") || textoNorm.contains("sin arrugas"))
            if (planchadoEsp) {
                etiquetas.add("Planchado especial")
            }

            etiquetas.toList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
