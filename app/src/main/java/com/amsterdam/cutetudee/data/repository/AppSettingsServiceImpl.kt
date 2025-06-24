package com.amsterdam.cutetudee.data.repository

import com.amsterdam.cutetudee.data.local.datastore.DataStore
import com.amsterdam.cutetudee.domain.service.AppSettingsService

class AppSettingsServiceImpl(
    private val dataStore: DataStore
): AppSettingsService {
    override suspend fun setOnBoardingIsShownToTrue() {
        dataStore.setIsOnBoardingFinished(true)
    }

    override suspend fun getOnBoardingIsShown(): Boolean {
        return dataStore.getIsOnBoardingFinished()
    }

    override suspend fun isDarkMode(): Boolean {
        return dataStore.isDarkMode()
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        dataStore.setDarkMode(isDarkMode)
    }
}