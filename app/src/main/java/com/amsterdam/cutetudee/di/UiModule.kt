package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.screens.category.CategoryViewModel
import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import com.amsterdam.cutetudee.presentation.screens.splash.SplashViewModel
import com.amsterdam.cutetudee.presentation.screens.tasks.TasksViewModel
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
import org.koin.android.ext.koin.androidContext
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.CategoryDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    single { UriToBitmapString(androidContext()) }
    single { ValidateImageSize(androidContext()) }
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::CategoryViewModel)
}