package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.repository.AppSettingsServiceImpl
import com.amsterdam.cutetudee.data.service.CategoryServiceImpl
import com.amsterdam.cutetudee.data.service.TaskServiceImpl
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import org.koin.dsl.module

val serviceModule = module {
    single<CategoryService> { CategoryServiceImpl(get()) }
    single<TaskService> { TaskServiceImpl(get()) }
    single<AppSettingsService> { AppSettingsServiceImpl(get()) }
}