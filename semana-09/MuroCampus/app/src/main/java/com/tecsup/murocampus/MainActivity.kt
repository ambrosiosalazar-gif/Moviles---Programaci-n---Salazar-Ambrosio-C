package com.tecsup.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.murocampus.data.PostRepositoryRemote
import com.tecsup.murocampus.data.remote.RetrofitClient
import com.tecsup.murocampus.ui.PantallaCrear
import com.tecsup.murocampus.ui.PantallaDetalle
import com.tecsup.murocampus.ui.PantallaLista
import com.tecsup.murocampus.ui.theme.MuroCampusTheme
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MuroCampusTheme {
                val vm: PostsViewModel = viewModel(
                    factory = PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))
                )
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }
                var creando by rememberSaveable { mutableStateOf(false) }
                val id = idSeleccionado

                when {
                    creando -> PantallaCrear(vm, onAtras = { creando = false })
                    id != null -> PantallaDetalle(vm, id, onAtras = { idSeleccionado = null })
                    else -> PantallaLista(
                        vm,
                        onPostClick = { idSeleccionado = it },
                        onCrear = { creando = true }
                    )
                }
            }
        }
    }
}