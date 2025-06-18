package com.amsterdam.cutetudee.presentation.component.custom_snack_bar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class CustomSnackBarVisuals(
    override val message: String,
    val status: CustomSnackBarStatus,
) : SnackbarVisuals {
    override val actionLabel: String? = null
    override val withDismissAction: Boolean = false
    override val duration: SnackbarDuration = SnackbarDuration.Short
}