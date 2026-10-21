package com.salazar.clinicasaludmas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PurplePrimary,
    primaryContainer = PurpleContainer,
    secondary = PurpleSecondary,
    background = Background,
    surface = Surface
)

private val DarkColors = darkColorScheme(
    primary = PurplePrimary,
    primaryContainer = PurpleSecondary,
    secondary = PurpleSecondary
)

@Composable
fun ClinicaSaludMasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // dynamicColor desactivado para mantener siempre la paleta morada de marca,
    // sin importar el wallpaper o el dispositivo del usuario
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
