package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun OverlayBoxContent(
    modifier: Modifier = Modifier,
    currentDate: String,
    numberOfCompletedTask: Int,
    numberOfInProgressTask: Int,
    numberOfToDoTask: Int,
    totalNumberOfTasks: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .align(Alignment.TopCenter)
                .zIndex(0f)
                .background(AppTheme.color.primary)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .zIndex(1f)
                .background(AppTheme.color.surfaceHigh)
        ) {
            IconDateTask(
                date = currentDate, icon = painterResource(id = R.drawable.date_icon)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    TextMoodIcon(
                        text = "Stay working!",
                        icon = painterResource(id = R.drawable.okay_emoji_icon),
                    )
                    Text(
                        text = "You've completed $numberOfCompletedTask out of $totalNumberOfTasks tasks Keep going!",
                        style = AppTheme.textStyle.body.small,
                        color = AppTheme.color.body,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    ImageMood(
                        image = painterResource(id = R.drawable.tudee_image_neutral),
                    )
                }
            }
            Text(
                text = "Overview",
                style = AppTheme.textStyle.title.large,
                color = AppTheme.color.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OverviewCard(
                    countOfTasks = numberOfCompletedTask,
                    tasksState = Task.Status.DONE,
                    backgroundColor = AppTheme.color.greenAccent,
                    modifier = Modifier.weight(1f)
                )
                OverviewCard(
                    countOfTasks = numberOfInProgressTask,
                    tasksState = Task.Status.IN_PROGRESS,
                    backgroundColor = AppTheme.color.yellowAccent,
                    modifier = Modifier.weight(1f)
                )
                OverviewCard(
                    countOfTasks = numberOfToDoTask,
                    tasksState = Task.Status.TODO,
                    backgroundColor = AppTheme.color.purpleAccent,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview
private fun OverlayBoxContentPreview() {
    OverlayBoxContent(
        currentDate = "22 Jun 2025",
        numberOfCompletedTask = 3,
        totalNumberOfTasks = 10,
        numberOfToDoTask = 8,
        numberOfInProgressTask = 1
    )
}