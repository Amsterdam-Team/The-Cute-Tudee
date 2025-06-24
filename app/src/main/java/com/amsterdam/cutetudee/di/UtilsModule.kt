package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.ThemeManager
import org.koin.dsl.module

val utilsModule = module {
    single<IDateTimeHandler> { DateTimeHandler() }
    single { ThemeManager(get()) }
}