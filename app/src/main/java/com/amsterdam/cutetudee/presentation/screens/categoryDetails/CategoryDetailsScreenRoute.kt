package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.navigation.Screen

fun NavGraphBuilder.categoryDetailsScreenRoute(
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    composable<Screen.CategoryDetails> {
        CategoryDetailsScreen(onShowSnackBar = onShowSnackBar)
    }
}