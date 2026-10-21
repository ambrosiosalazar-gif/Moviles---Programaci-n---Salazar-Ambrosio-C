package com.salazar.clinicasaludmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.salazar.clinicasaludmas.navigation.AppNavigation
import com.salazar.clinicasaludmas.ui.theme.ClinicaSaludMasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClinicaSaludMasTheme {
                AppNavigation()
            }
        }
    }
}
