package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.AppViewModel
import com.amsterdam.cutetudee.presentation.screens.category.CategoryViewModel
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.CategoryDetailsViewModel
import com.amsterdam.cutetudee.presentation.screens.home.HomeViewModel
import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import com.amsterdam.cutetudee.presentation.screens.splash.SplashViewModel
import com.amsterdam.cutetudee.presentation.screens.tasks.AddEditTaskViewModel
import com.amsterdam.cutetudee.presentation.screens.tasks.TasksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::AddEditTaskViewModel)
}