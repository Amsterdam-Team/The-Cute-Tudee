package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
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
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        scrimColor = scrimColor,
        properties = ModalBottomSheetDefaults.properties,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = dragHandle,
        content = content,
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
fun CustomBottomSheetPreview() {
    CustomBottomSheet(
        onDismissRequest = {},
        content = {}
    )
}




