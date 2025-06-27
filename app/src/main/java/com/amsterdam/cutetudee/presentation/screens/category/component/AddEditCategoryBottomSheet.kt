package com.amsterdam.cutetudee.presentation.screens.category.component

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomTextField
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.theme.LocalIsDarkTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCategoryBottomSheet(
    text: String,
    image: Uri,
    isLoading: Boolean,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    isEdit: Boolean = false,
    hideBottomSheet: Boolean = false,
    onDeleteCategory: () -> Unit = {},
    onCancel: () -> Unit = {},
    onAddCategory: () -> Unit,
    onDismissRequest: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onTextValueChange: (String) -> Unit,
) {
    if (hideBottomSheet)
        return

    CustomBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = if (isEdit) stringResource(R.string.edit_category) else stringResource(R.string.add_category),
                    color = AppTheme.color.title,
                    style = AppTheme.textStyle.title.large,
                    modifier = Modifier
                )
                AnimatedVisibility(isEdit) {
                    Text(
                        text = stringResource(R.string.delete),
                        color = AppTheme.color.error,
                        style = AppTheme.textStyle.label.large,
                        modifier = Modifier.clickable(onClick = onDeleteCategory)
                    )
                }
            }
            CustomTextField(
                text = text,
                borderColor = AppTheme.color.stroke,
                hintText = stringResource(R.string.category_title_hint),
                onValueChange = { onTextValueChange(it) },
                leadingIcon = R.drawable.menu_circle_icon,
                maxCharacters=60
            )
            Text(
                text = stringResource(R.string.category_image),
                color = AppTheme.color.title,
                style = AppTheme.textStyle.title.medium,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )
            ImagePicker(
                modifier = Modifier.align(Alignment.Start),
                image = image,
            ) {
                onImageSelected(it)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RectangleShape,
                    ambientColor = AppTheme.color.dropShadowColor,
                    spotColor = AppTheme.color.dropShadowColor,
                    clip = false
                )
                .background(AppTheme.color.surfaceHigh)
                .border(
                    width = 1.dp,
                    color = AppTheme.color.surfaceHigh
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
        ) {
            GradientFilledButton(
                title = stringResource(if (isEdit) R.string.save else R.string.add),
                onClick = {
                    onAddCategory()
                },
                isLoading = isLoading,
                isEnabled = isEnabled,
                isNegative = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            )
            OutlineButton(
                text = stringResource(R.string.cancel),
                onClick = onCancel,
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun AddEditCategoryBottomSheetPreview() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        AddEditCategoryBottomSheet(
            text = "",
            image = Uri.EMPTY,
            isLoading = false,
            isEnabled = false,
            isEdit = true,
            onAddCategory = {},
            onDismissRequest = { },
            onImageSelected = {}
        ) {}
    }
}