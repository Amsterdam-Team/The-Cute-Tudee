package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.MainViewModel
import com.amsterdam.cutetudee.data.repository.AppSettingsServiceImpl
import com.amsterdam.cutetudee.presentation.screens.category.CategoryViewModel
import com.amsterdam.cutetudee.presentation.screens.home.HomeViewModel
import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import com.amsterdam.cutetudee.presentation.screens.splash.SplashViewModel
import com.amsterdam.cutetudee.presentation.screens.tasks.AddEditTaskViewModel
import com.amsterdam.cutetudee.presentation.screens.tasks.TasksViewModel
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule =
    module {
        viewModelOf(::OnBoardingViewModel)
        viewModelOf(::TasksViewModel)
        viewModelOf(::SplashViewModel)
        single { UriToBitmapString(androidContext()) }
        single { ValidateImageSize(androidContext()) }
        viewModelOf(::CategoryViewModel)
        single<IDateTimeHandler> { DateTimeHandler() }
        single { AppSettingsServiceImpl(get()) }
        viewModelOf(::HomeViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::AddEditTaskViewModel)
    }