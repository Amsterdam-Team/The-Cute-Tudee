package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { (CustomSnackBarViewModel()) }
}