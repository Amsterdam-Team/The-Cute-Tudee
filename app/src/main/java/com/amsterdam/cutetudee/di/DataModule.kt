package com.amsterdam.cutetudee.di

import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.datastore.AppPreferences
import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single<TudeeDatabase> { TudeeDatabase.getInstance(androidApplication()) }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidApplication().dataStoreFile("app.preferences_pb") }
        )
    }
    single { AppPreferences(
        dataStore = get<DataStore<Preferences>>(),
    ) }
    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }
}