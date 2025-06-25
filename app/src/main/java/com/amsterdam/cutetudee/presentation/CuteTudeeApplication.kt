package com.amsterdam.cutetudee.presentation

import android.app.Application
import com.amsterdam.cutetudee.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CuteTudeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CuteTudeeApplication)
            modules(appModule)
        }
    }
}