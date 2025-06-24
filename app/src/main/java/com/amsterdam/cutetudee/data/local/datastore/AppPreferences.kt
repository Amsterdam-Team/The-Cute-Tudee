package com.amsterdam.cutetudee.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppPreferences(
    private val dataStore: DataStore<Preferences>,
) {

    companion object {
        const val ON_BOARDING = "onBoarding"
        const val DARK_MODE = "darkMode"
    }

    // Function to get onboarding finished status
    suspend fun getIsOnBoardingFinished(): Boolean {
        return dataStore.data.map { settings ->
            settings[booleanPreferencesKey(ON_BOARDING)] ?: false
        }.first()
    }

    // Function to set onboarding finished status
    suspend fun setIsOnBoardingFinished(isOnBoardingFinished: Boolean) {
        dataStore.edit { settings ->
            settings[booleanPreferencesKey(ON_BOARDING)] = isOnBoardingFinished
        }
    }

    // Function to get dark mode status
    fun getAppDarkModeOn(): Flow<Boolean> {
        return dataStore.data.map { settings ->
            settings[booleanPreferencesKey(DARK_MODE)] ?: false
        }
    }

    // Function to set dark mode status
    suspend fun setAppDarkModeOn(isDarkModeOn: Boolean) {
        dataStore.edit { settings ->
            settings[booleanPreferencesKey(DARK_MODE)] = isDarkModeOn
        }
    }
}
