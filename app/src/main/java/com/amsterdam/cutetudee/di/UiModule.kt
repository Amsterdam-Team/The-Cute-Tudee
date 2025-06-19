package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.screens.categoryDetails.CategoryDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::CategoryDetailsViewModel)
}