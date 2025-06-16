package com.amsterdam.cutetudee.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{

    @Serializable
    data object OnBoarding : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Tasks : Screen()

    @Serializable
    data object Categories : Screen()

}