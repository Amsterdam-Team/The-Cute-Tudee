package com.amsterdam.cutetudee

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.amsterdam.cutetudee.di.serviceModule
import com.amsterdam.cutetudee.di.testDataModule
import com.amsterdam.cutetudee.di.viewModelModule
import com.amsterdam.cutetudee.utils.viewModelModuleTest
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.GlobalContext.startKoin

class TestingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            modules(testDataModule, serviceModule , viewModelModuleTest)
        }
    }
}