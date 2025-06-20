package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import com.amsterdam.cutetudee.presentation.screens.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::SplashViewModel)
}