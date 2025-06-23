package com.amsterdam.cutetudee.di

import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.datastore.DataStore
import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single<TudeeDatabase> { TudeeDatabase.getInstance(androidApplication()) }
    single<DataStore> { DataStore(androidApplication()) }
    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }
}