package com.amsterdam.cutetudee.presentation.navigation

import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Splash : Screen()

    @Serializable
    data object OnBoarding : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data class Tasks(
        val status: TaskStatusUi? = null,
    ) : Screen()

    @Serializable
    data object Categories : Screen()

    @Serializable
    data class CategoryDetails(
        val categoryId: String,
    ) : Screen()
}
