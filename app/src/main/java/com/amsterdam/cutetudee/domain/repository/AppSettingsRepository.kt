package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.Theme

interface AppSettingsRepository {
    fun setOnBoardingIsShownToTrue()
    fun getOnBoardingIsShown(): Boolean
    fun getPreferredTheme():Theme
    fun setPreferredTheme(theme: Theme)
}