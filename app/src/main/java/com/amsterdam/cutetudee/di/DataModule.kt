package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import com.amsterdam.cutetudee.data.repository.CategoryServiceImpl
import com.amsterdam.cutetudee.data.repository.TaskServiceImpl
import com.amsterdam.cutetudee.domain.repository.CategoryService
import com.amsterdam.cutetudee.domain.repository.TaskService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single {
        TudeeDatabase.getInstance(androidApplication())
    }

    single {
        get<TudeeDatabase>().categoryDao()
        get<TudeeDatabase>().taskDao()
    }

    single<CategoryService> {
        CategoryServiceImpl(get())
    }

    single<TaskService> {
        TaskServiceImpl(get())
    }
}
