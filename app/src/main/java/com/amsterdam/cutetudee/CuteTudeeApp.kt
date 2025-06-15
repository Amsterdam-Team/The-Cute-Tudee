package com.amsterdam.cutetudee

import android.app.Application
import com.amsterdam.cutetudee.di.dataModule
import com.amsterdam.cutetudee.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CuteTudeeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CuteTudeeApp)
            modules(uiModule, dataModule)
        }
    }
}