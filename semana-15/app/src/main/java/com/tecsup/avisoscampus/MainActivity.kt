package com.tecsup.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.tecsup.avisoscampus.data.AvisosRepositoryMemoria
import com.tecsup.avisoscampus.ui.PantallaAvisos
import com.tecsup.avisoscampus.ui.theme.AvisosCampusTheme
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel
import com.tecsup.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {

    private val vm: AvisosViewModel by viewModels {
        AvisosViewModelFactory(AvisosRepositoryMemoria())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvisosCampusTheme {
                PantallaAvisos(vm)
            }
        }
    }
}