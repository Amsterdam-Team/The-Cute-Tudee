package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.datastore.DataStore
import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import com.amsterdam.cutetudee.data.repository.AppSettingsServiceImpl
import com.amsterdam.cutetudee.data.service.CategoryServiceImpl
import com.amsterdam.cutetudee.data.service.TaskServiceImpl
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule =
    module {
        single<TudeeDatabase> { TudeeDatabase.getInstance(androidApplication()) }
        single<DataStore> { DataStore(androidApplication()) }
        single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
        single<TaskDao> { get<TudeeDatabase>().taskDao() }
        single<CategoryService> { CategoryServiceImpl(get()) }
        single<TaskService> { TaskServiceImpl(get()) }
        single<AppSettingsService> { AppSettingsServiceImpl(get()) }
        single {
            val dateTimeHandler: IDateTimeHandler = DateTimeHandler()

            dateTimeHandler
        }
    }
