package com.amsterdam.cutetudee.presentation.component.custom_snack_bar

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

enum class CustomSnackBarStatus(@DrawableRes val icon: Int) {
    Success(R.drawable.success_checkmark_icon),
    Failure(R.drawable.error_checmarck_icon);

    val iconContainerColor: Color
        @Composable
        get() = when (this) {
            Success -> AppTheme.color.greenVariant
            Failure -> AppTheme.color.errorVariant
        }

    val iconTintColor: Color
        @Composable
        get() = when (this) {
            Success -> AppTheme.color.greenAccent
            Failure -> AppTheme.color.error
        }
}

