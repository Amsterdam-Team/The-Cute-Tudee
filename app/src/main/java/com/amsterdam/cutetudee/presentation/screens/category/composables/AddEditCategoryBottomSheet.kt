package com.amsterdam.cutetudee.presentation.screens.category.composables

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomTextField
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme

@Composable
fun AddEditCategoryBottomSheet(
    text: String,
    image: Uri,
    isLoading: Boolean,
    isEnabled: Boolean,
    isEdit: Boolean = false,
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onTextValueChange: (String) -> Unit
){
    CustomBottomSheet {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = stringResource(if (isEdit) R.string.edit_category else R.string.add_category),
                color = AppTheme.color.title,
                style = AppTheme.textStyle.title.large,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.Start)
            )
            CustomTextField(
                text = text,
                hintText = stringResource(R.string.category_title_hint),
                onValueChange = { onTextValueChange(it) },
                leadingIcon = R.drawable.menu_circle_icon,
                modifier = Modifier.padding(bottom = 12.dp)
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
                onImageSelected = { onImageSelected(it) }
            )
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
                onClick = onConfirmation,
                isLoading = isLoading,
                isEnabled = isEnabled,
                isNegative = false,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlineButton(
                text = stringResource(R.string.cancel),
                onClick = onDismissRequest,
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun AddEditCategoryBottomSheetPreview(){
    CuteTudeeTheme(isDarkTheme = true) {
        AddEditCategoryBottomSheet(
            text = "",
            onTextValueChange = {},
            isEnabled = true,
            isLoading = false,
            isEdit = true,
            onConfirmation = {},
            onDismissRequest = {  },
            onImageSelected = {},
            image = Uri.EMPTY
        )
    }
}