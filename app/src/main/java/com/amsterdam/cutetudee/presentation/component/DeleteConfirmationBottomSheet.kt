package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun DeleteConfirmationBottomSheet(
    modifier: Modifier = Modifier,
) {
    CustomBottomSheet(modifier) {
        ConfirmationMessageContainer()
        ConfirmationButtonContainer()
    }
}

@Composable
private fun ConfirmationButtonContainer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RectangleShape,
                // todo when color is added will be replaced here
                ambientColor = AppTheme.color.surface,
                spotColor = AppTheme.color.surface,
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
        NavigationButton()
        Spacer(Modifier.height(12.dp))
        OutlineButton()
    }
}

@Composable
private fun ConfirmationMessageContainer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.color.surface)
            .padding(horizontal = 16.dp)
    )
    {
        Text(
            text = stringResource(R.string.delete_task),
            style = AppTheme.textStyle.title.large,
            color = AppTheme.color.title
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.delete_description),
            style = AppTheme.textStyle.body.large,
            color = AppTheme.color.body
        )
        Spacer(modifier = Modifier.height(12.dp))
        //todo  when image added will be replaced here
        Image(
            painter = painterResource(R.drawable.tudee_image_sad),
            contentDescription = null,
            modifier = Modifier
                .width(107.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteConfirmationBottomSheetPreview(){
    DeleteConfirmationBottomSheet()
}
