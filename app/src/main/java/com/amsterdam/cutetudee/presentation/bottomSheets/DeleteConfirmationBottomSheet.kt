package com.amsterdam.cutetudee.presentation.bottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun DeleteConfirmationBottomSheet(
    title: String,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CustomBottomSheet(modifier) {
        ConfirmationMessageContainer(title = title)
        ConfirmationButtonContainer(onDelete, onCancel)
    }
}

@Composable
private fun ConfirmationButtonContainer(
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .dropShadow(
                    offsetY = 4.dp,
                    blur = 20.dp,
                    shape = RectangleShape,
                ).background(AppTheme.color.surfaceHigh)
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
    ) {
        GradientFilledButton(
            title = stringResource(R.string.delete),
            onClick = onDelete,
            isLoading = false,
            isNegative = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlineButton(
            text = stringResource(R.string.cancel),
            onClick = onCancel,
            isLoading = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ConfirmationMessageContainer(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(AppTheme.color.surface)
                .padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = title,
            style = AppTheme.textStyle.title.large,
            color = AppTheme.color.title,
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.delete_description),
            style = AppTheme.textStyle.body.large,
            color = AppTheme.color.body,
        )
        Image(
            painter = painterResource(R.drawable.tudee_image_shocking),
            contentDescription = null,
            modifier =
                Modifier
                    .padding(bottom = 24.dp)
                    .width(107.dp)
                    .align(Alignment.CenterHorizontally),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteConfirmationBottomSheetPreview() {
    DeleteConfirmationBottomSheet(
        title = stringResource(R.string.delete_task),
        onDelete = {},
        onCancel = {},
    )
}
