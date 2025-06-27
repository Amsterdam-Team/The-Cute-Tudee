package com.amsterdam.cutetudee.presentation.component

import android.app.Activity
import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.LocalIsDarkTheme
import com.amsterdam.cutetudee.presentation.theme.colors.darkThemeColors
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun CustomBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = AppTheme.color.surface,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { DragHandler() },
    content: @Composable ColumnScope.() -> Unit,
) {

    ModalBottomSheet(
        modifier = modifier.statusBarsPadding(),
        shape = shape,
        containerColor = containerColor,
        scrimColor = scrimColor,
        properties = ModalBottomSheetDefaults.properties,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = dragHandle,
        content = {
            content()
            val view = LocalView.current
            val isDarkTheme = LocalIsDarkTheme.current
            (view.parent as? DialogWindowProvider)?.window?.let { window ->
                SideEffect {
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                        !isDarkTheme
                }
            }
        },
        contentWindowInsets = {
            BottomSheetDefaults.windowInsets.exclude(WindowInsets.navigationBars)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DragHandler() {
    DragHandle(
        width = 32.dp,
        height = 4.dp,
        color = AppTheme.color.body,
        shape = CircleShape
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemeAndLocalePreviews
@Composable
private fun CustomBottomSheetPreview() {
    CustomBottomSheet(
        onDismissRequest = {},
        content = {}
    )
}




