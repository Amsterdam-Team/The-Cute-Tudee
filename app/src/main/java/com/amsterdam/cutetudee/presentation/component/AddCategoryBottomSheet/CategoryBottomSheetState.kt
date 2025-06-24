package com.amsterdam.cutetudee.presentation.component.AddCategoryBottomSheet

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter

data class CategoryBottomSheetUIState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val painter: Painter? = null,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)