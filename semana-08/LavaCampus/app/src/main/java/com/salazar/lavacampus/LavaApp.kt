package com.salazar.lavacampus

import android.app.Application
import com.salazar.lavacampus.data.local.DatosIniciales
import com.salazar.lavacampus.data.local.LavaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LavaApp : Application() {
    val database: LavaDatabase by lazy { LavaDatabase.obtener(this) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            DatosIniciales.sembrar(database)
        }
    }
}
