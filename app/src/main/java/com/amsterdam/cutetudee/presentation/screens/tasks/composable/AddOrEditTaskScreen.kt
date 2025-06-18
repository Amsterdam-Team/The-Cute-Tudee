package com.amsterdam.cutetudee.presentation.screens.tasks.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomTextField
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun AddOrEditTaskScreen(
    modifier: Modifier = Modifier,
) {
    AddOrEditScreenContent(modifier)
}

@Composable
fun AddOrEditScreenContent(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
    ) {
        CustomBottomSheet(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                item {
                    Text(
                        text = stringResource(R.string.add_task),
                        style = AppTheme.textStyle.title.large,
                        color = AppTheme.color.title,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                item {
                    CustomTextField(
                        text = "",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(),
                        style = AppTheme.textStyle.label.medium,
                        hintText = stringResource(R.string.task_title_hint),
                        maxLines = 1,
                        leadingIcon = R.drawable.note_icon,
                        borderColor = AppTheme.color.stroke,
                        borderFocusedColor = AppTheme.color.primary,
                        onValueChange = {}
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = RectangleShape,
                    color = AppTheme.color.dropShadowColor,
                )
                .background(AppTheme.color.surfaceHigh)
                .align(Alignment.BottomStart),
        ) {
            GradientFilledButton(
                title = stringResource(R.string.add),
                onClick = {},
                isLoading = false,
                isNegative = false,
                isEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                paddingValues = PaddingValues(horizontal = 16.dp, vertical = 18.5.dp)
            )

            OutlineButton(
                text = stringResource(R.string.cancel),
                onClick = {},
                isLoading = false,
                isEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 12.dp, bottom = 12.dp),
                textButtonPadding = PaddingValues(vertical = 18.5.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AddOrEditTaskScreenPreview() {
    AddOrEditTaskScreen()

}