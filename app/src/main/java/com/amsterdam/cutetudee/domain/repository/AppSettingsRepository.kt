package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.Mode

interface AppSettingsRepository {
    fun setOnBoardingIsShownToTrue()
    fun getOnBoardingIsShown(): Boolean
    fun getPreferredMode():Mode
    fun setPreferredMode(theme: Mode)
}