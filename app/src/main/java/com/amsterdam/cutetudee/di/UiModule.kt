package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.screens.categoryDetails.CategoryDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import com.amsterdam.cutetudee.presentation.screens.onBoarding.OnBoardingViewModel
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::OnBoardingViewModel)
}