package com.amsterdam.cutetudee.presentation.screens.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.categoryScreenRoute(
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    composable<Screen.Categories> {
        CategoryScreen(onShowSnackBar = onShowSnackBar)
    }
}