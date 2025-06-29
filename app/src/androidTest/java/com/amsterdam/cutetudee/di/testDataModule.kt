package com.amsterdam.cutetudee.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.room.Room
import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.datastore.AppPreferences
import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import com.amsterdam.cutetudee.utils.PredefinedCategorySeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.io.File

val testDataModule = module() {

    single<TudeeDatabase> {
       val roomDB =  Room.inMemoryDatabaseBuilder(androidApplication(), TudeeDatabase::class.java)
           .build()
        PredefinedCategorySeeder(roomDB)
        roomDB
    }

    single<AppPreferences> {
        val testDataStore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { File.createTempFile("test_prefs", ".preferences_pb") }
        )
        AppPreferences(testDataStore)
    }

    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }
}