package com.amsterdam.cutetudee.data.local.datastore

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppPreferencesDataStore(
    context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PreferencesDataStore {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun getBooleanPreference(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)

    }

    private suspend fun setBooleanPreference(key: String, value: Boolean) {
        withContext(dispatcher) {
            preferences.edit().apply {
                putBoolean(key, value)
                apply()
            }
        }
    }

    override suspend fun isAppLaunchForFirstTime(): Boolean {
        return getBooleanPreference(KEY_FIRST_LAUNCH, true)
    }

    override suspend fun setAppLaunchIsDone() {
        setBooleanPreference(KEY_FIRST_LAUNCH, false)
    }

    override fun isOnboardingCompleted(): Boolean {
        return getBooleanPreference(KEY_ONBOARDING_COMPLETED, false)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        setBooleanPreference(KEY_ONBOARDING_COMPLETED, completed)
    }

    override fun isDarkTheme(): Boolean {
        return getBooleanPreference(KEY_DARK_THEME, false)
    }

    override suspend fun setDarkTheme(isDark: Boolean) {
        return setBooleanPreference(KEY_DARK_THEME, isDark)
    }

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_DARK_THEME = "dark_theme"
    }


}
