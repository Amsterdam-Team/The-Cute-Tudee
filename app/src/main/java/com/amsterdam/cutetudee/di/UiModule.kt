package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.MainViewModel
import com.amsterdam.cutetudee.data.repository.AppSettingsServiceImpl
import com.amsterdam.cutetudee.presentation.screens.home.HomeViewModel
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::HomeViewModel)
    single<IDateTimeHandler> { DateTimeHandler() }
    single { AppSettingsServiceImpl(get()) }
    viewModelOf(::MainViewModel)
}