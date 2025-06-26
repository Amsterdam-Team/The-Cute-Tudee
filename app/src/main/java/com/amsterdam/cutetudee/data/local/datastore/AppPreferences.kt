package com.amsterdam.cutetudee.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.amsterdam.cutetudee.data.local.datastore.AppPreferences.PreferencesKeys.DARK_MODE
import com.amsterdam.cutetudee.data.local.datastore.AppPreferences.PreferencesKeys.ON_BOARDING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppPreferences(
    private val dataStore: DataStore<Preferences>,
) {

    private object PreferencesKeys {
        val ON_BOARDING = booleanPreferencesKey("onBoarding")
        val DARK_MODE = booleanPreferencesKey("darkMode")
    }


    suspend fun getIsOnBoardingFinished(): Boolean {
        return dataStore.data.map { settings ->
            settings[ON_BOARDING] ?: false
        }.first()
    }

    suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) {
        dataStore.edit { settings ->
            settings[ON_BOARDING] = isOnBoardingFinished
        }
    }

    fun getAppDarkModeOn(): Flow<Boolean> {
        return dataStore.data.map { settings ->
            settings[DARK_MODE] ?: false
        }
    }

    suspend fun setAppDarkModeOn(isDarkModeOn: Boolean) {
        dataStore.edit { settings ->
            settings[DARK_MODE] = isDarkModeOn
        }
    }
}
