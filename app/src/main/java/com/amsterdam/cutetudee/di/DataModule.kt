package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import com.amsterdam.cutetudee.data.repository.CategoryRepositoryImpl
import com.amsterdam.cutetudee.data.repository.TaskRepositoryImpl
import com.amsterdam.cutetudee.domain.repository.CategoryRepository
import com.amsterdam.cutetudee.domain.repository.TaskRepository
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

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }

    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
}
