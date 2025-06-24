package com.amsterdam.cutetudee.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


private val Context.dataStore by preferencesDataStore("user_data")

class DataStore(
    appCtx: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val mDataStore by lazy {
        appCtx.dataStore
    }

    companion object {
        const val ON_BOARDING = "onBoarding"
        const val DARK_MODE = "darkMode"
    }

    suspend fun getIsOnBoardingFinished(): Boolean = withContext(dispatcher) {
        mDataStore.data.map { settings ->
            settings[booleanPreferencesKey(ON_BOARDING)] ?: false
        }.first()
    }

    suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) {
        withContext(dispatcher) {
            mDataStore.edit { settings ->
                settings[booleanPreferencesKey(ON_BOARDING)] = isOnBoardingFinished
            }
        }
    }

    suspend fun isDarkMode(): Boolean = withContext(dispatcher) {
        mDataStore.data.map { settings ->
            settings[booleanPreferencesKey(DARK_MODE)] ?: false
        }.first()
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        withContext(dispatcher) {
            mDataStore.edit { settings ->
                settings[booleanPreferencesKey(DARK_MODE)] = isDarkMode
            }
        }
    }
}