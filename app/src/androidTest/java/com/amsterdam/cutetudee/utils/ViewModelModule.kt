package com.amsterdam.cutetudee.utils

import com.amsterdam.cutetudee.presentation.AppViewModel
import com.amsterdam.cutetudee.presentation.screens.category.CategoryViewModel
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.CategoryDetailsViewModel
import com.amsterdam.cutetudee.presentation.screens.home.HomeViewModel
import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import com.amsterdam.cutetudee.presentation.screens.splash.SplashViewModel
import com.amsterdam.cutetudee.presentation.screens.tasks.TasksViewModel
import com.amsterdam.cutetudee.presentation.utils.dispatcher.DefaultDispatcherProvider
import com.amsterdam.cutetudee.presentation.utils.dispatcher.DispatcherProvider
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModuleTest = module {
    singleOf<DispatcherProvider>(::TestDispatcherProvider)
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::CategoryDetailsViewModel)
}