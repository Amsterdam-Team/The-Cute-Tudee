package com.amsterdam.cutetudee.data.local.datastore

interface PreferencesDataStore {
    suspend fun isAppLaunchForFirstTime(): Boolean

    suspend fun setAppLaunchIsDone()

    suspend fun isOnboardingCompleted(): Boolean

    suspend fun setOnboardingCompleted(completed: Boolean = true)

    fun isDarkTheme(): Boolean

    suspend fun setDarkTheme(isDark: Boolean)
}