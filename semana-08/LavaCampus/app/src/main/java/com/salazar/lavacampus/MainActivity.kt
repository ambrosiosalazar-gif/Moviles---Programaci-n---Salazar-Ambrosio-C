package com.salazar.lavacampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salazar.lavacampus.ui.navigation.NavLava
import com.salazar.lavacampus.ui.theme.LavaTheme
import com.salazar.lavacampus.viewmodel.LavaViewModelFactory
import com.salazar.lavacampus.viewmodel.SesionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as LavaApp
        setContent {
            LavaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val sesionViewModel: SesionViewModel = viewModel(
                        factory = LavaViewModelFactory(app.repository, app.prefsStore)
                    )
                    NavLava(
                        repository = app.repository,
                        prefsStore = app.prefsStore,
                        sesionViewModel = sesionViewModel
                    )
                }
            }
        }
    }
}
