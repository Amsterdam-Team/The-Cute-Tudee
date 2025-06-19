package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import com.amsterdam.cutetudee.data.service.CategoryServiceImpl
import com.amsterdam.cutetudee.data.service.TaskServiceImpl
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single {
        TudeeDatabase.getInstance(androidApplication())
    }

    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }

    single<CategoryService> {
        CategoryServiceImpl(get())
    }

    single<TaskService> {
        TaskServiceImpl(get())
    }
}
