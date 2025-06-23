package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.ThemeManager
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {
    single<IDateTimeHandler> { DateTimeHandler() }
    single { ThemeManager(get()) }
    single { UriToBitmapString(androidContext()) }
    single { ValidateImageSize(androidContext()) }
}