package com.salazar.lavacampus

import android.app.Application
import com.salazar.lavacampus.data.local.DatosIniciales
import com.salazar.lavacampus.data.local.LavaDatabase
import com.salazar.lavacampus.data.prefs.PreferenciasStore
import com.salazar.lavacampus.data.repository.LavaRepository
import com.salazar.lavacampus.data.repository.LavaRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LavaApp : Application() {
    val database: LavaDatabase by lazy { LavaDatabase.obtener(this) }
    val repository: LavaRepository by lazy { LavaRepositoryRoom(database) }
    val prefsStore: PreferenciasStore by lazy { PreferenciasStore(this) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            DatosIniciales.sembrar(database)
        }
    }
}
