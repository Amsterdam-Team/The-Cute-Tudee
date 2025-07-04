package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.categoryDetailsScreenRoute(
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    composable<Screen.CategoryDetails>(
        enterTransition = {
            slideInHorizontally { it / 3 } + fadeIn(animationSpec = tween(300, delayMillis = 90))
        },
        exitTransition = {
            slideOutHorizontally { -it / 3 } + fadeOut(animationSpec = tween(90))
        },
    ) {
        CategoryDetailsScreen(onShowSnackBar = onShowSnackBar)
    }
}