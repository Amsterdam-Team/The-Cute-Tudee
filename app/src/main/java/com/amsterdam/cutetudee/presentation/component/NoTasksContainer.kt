package com.amsterdam.cutetudee.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun NoTasksContainer(
    primaryMessage: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.tudee_no_tasks_image),
            contentDescription = stringResource(id = R.string.empty_tasks_title),
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .height(148.dp),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(end = 110.dp),
        ) {
            val containerCornerShape =
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 2.dp,
                )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .dropShadow(
                            shape = containerCornerShape,
                            blur = 12.dp,
                            offsetY = 4.dp,
                        ).padding(end = 20.dp)
                        .clip(containerCornerShape)
                        .background(AppTheme.color.surfaceHigh)
                        .padding(vertical = 8.dp, horizontal = 12.dp),
            ) {
                Text(
                    text = primaryMessage,
                    color = AppTheme.color.body,
                    style = AppTheme.textStyle.title.small,
                )
                Text(
                    text = stringResource(id = R.string.empty_screen_description),
                    color = AppTheme.color.hint,
                    style = AppTheme.textStyle.body.small,
                )
            }

            TudeeThinkingDots(
                modifier =
                    Modifier
                        .align(Alignment.End)
                        .padding(top = 3.dp),
            )
        }
    }
}

@Composable
private fun TudeeThinkingDots(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        ThinkingDot(
            modifier =
                Modifier
                    .align(Alignment.Start)
                    .padding(end = 9.dp)
                    .size(14.dp),
        )
        ThinkingDot(
            modifier =
                Modifier
                    .align(Alignment.End)
                    .padding(top = 3.dp, bottom = 5.dp, end = 5.dp)
                    .size(8.dp),
        )
        ThinkingDot(
            modifier =
                Modifier
                    .align(Alignment.End)
                    .padding(end = 1.dp)
                    .size(4.dp),
        )
    }
}

@Composable
private fun ThinkingDot(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .dropShadow(
                    shape = CircleShape,
                    blur = 12.dp,
                    offsetY = 4.dp,
                ).clip(CircleShape)
                .background(AppTheme.color.surfaceHigh),
    )
}

@Preview(name = "NoTasksHere")
@Preview(
    name = "NoTasksHere",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_UNDEFINED,
)
@Preview(name = "NoTasksHere", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun PreviewNoTasksHere() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        NoTasksContainer(
            "No tasks for today!",
            modifier =
                Modifier
                    .background(AppTheme.color.surface),
        )
    }
}
