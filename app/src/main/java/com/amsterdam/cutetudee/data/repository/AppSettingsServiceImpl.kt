package com.amsterdam.cutetudee.data.repository

import com.amsterdam.cutetudee.data.local.datastore.DataStore
import com.amsterdam.cutetudee.domain.model.ThemeMode
import com.amsterdam.cutetudee.domain.repository.AppSettingsService

class AppSettingsServiceImpl(
    private val dataStore: DataStore
): AppSettingsService {
    override suspend fun setOnBoardingIsShownToTrue() {
        dataStore.setIsOnBoardingFinished(true)
    }

    override suspend fun getOnBoardingIsShown(): Boolean {
        return dataStore.getIsOnBoardingFinished()
    }

    override suspend fun getPreferredMode(): ThemeMode {
        return if (dataStore.getAppDarkModeOn()) ThemeMode.DARK else ThemeMode.LIGHT
    }

    override suspend fun setPreferredMode(themeMode: ThemeMode) {
        dataStore.setAppDarkModeOn(themeMode == ThemeMode.DARK)
    }
}