package com.amsterdam.cutetudee.domain.service

interface AppSettingsService {
    suspend fun setOnBoardingIsShownToTrue()
    suspend fun getOnBoardingIsShown(): Boolean
    suspend fun isDarkMode():Boolean
    suspend fun setDarkMode(isDarkMode: Boolean)
}