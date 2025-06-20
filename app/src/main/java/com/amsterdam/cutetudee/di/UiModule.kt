package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.repository.AppSettingsServiceImpl
import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(_root_ide_package_.com.amsterdam.cutetudee.presentation.screens.tasks::TasksViewModel)
    viewModelOf(::SplashViewModel)
    single<IDateTimeHandler> { DateTimeHandler() }
    single { AppSettingsServiceImpl(get()) }
    viewModelOf(_root_ide_package_.com.amsterdam.cutetudee::MainViewModel)
}