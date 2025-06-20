package com.amsterdam.cutetudee.presentation.navigation

import com.amsterdam.cutetudee.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Splash: Screen()

    @Serializable
    data object OnBoarding: Screen()

    @Serializable
    data object Home: Screen()

    @Serializable
    data class Tasks(val status: Task.Status? = null): Screen()

    @Serializable
    data object Categories: Screen()

    @Serializable
    data class CategoryDetails(val categoryId: String): Screen()
}