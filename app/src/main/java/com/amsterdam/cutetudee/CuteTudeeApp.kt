package com.amsterdam.cutetudee

import android.app.Application
import com.amsterdam.cutetudee.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CuteTudeeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CuteTudeeApp)
            modules(appModule)
        }
    }
}