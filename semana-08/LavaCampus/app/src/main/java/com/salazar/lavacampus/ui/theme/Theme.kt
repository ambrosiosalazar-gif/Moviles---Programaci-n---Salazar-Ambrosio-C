package com.salazar.lavacampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.salazar.lavacampus.model.Rol

private val EstudianteLightColorScheme = lightColorScheme(
    primary = PrimaryEstudiante,
    primaryContainer = ContainerEstudiante,
    background = BackgroundEstudiante,
    surface = Color.White
)

private val AdminLightColorScheme = lightColorScheme(
    primary = PrimaryAdmin,
    primaryContainer = ContainerAdmin,
    background = BackgroundAdmin,
    surface = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryEstudiante,
    primaryContainer = Color(0xFF164E63),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B)
)

@Composable
fun LavaTheme(
    rol: Rol = Rol.CLIENTE,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        rol == Rol.ADMIN -> AdminLightColorScheme
        else -> EstudianteLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
