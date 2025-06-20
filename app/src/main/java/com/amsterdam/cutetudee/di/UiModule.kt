package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.screens.category.CategoryViewModel
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    single { UriToBitmapString(androidContext()) }
    single { ValidateImageSize(androidContext()) }
    viewModelOf(::CategoryViewModel)
}