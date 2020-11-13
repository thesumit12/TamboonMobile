package com.example.tamboonmobile

import android.app.Application
import com.example.tamboonmobile.di.appModule
import org.koin.android.ext.android.startKoin

class TamboonApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin(this, appModule)
    }
}