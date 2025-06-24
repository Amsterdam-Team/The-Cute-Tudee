package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import org.koin.dsl.module

val utilsModule = module {
    single<IDateTimeHandler> { DateTimeHandler() }
}