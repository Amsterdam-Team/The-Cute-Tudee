package com.amsterdam.cutetudee.presentation.screens.tasks.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CategoryItem
import com.amsterdam.cutetudee.presentation.component.CustomBottomSheet
import com.amsterdam.cutetudee.presentation.component.CustomTextField
import com.amsterdam.cutetudee.presentation.component.GradientFilledButton
import com.amsterdam.cutetudee.presentation.component.OutlineButton
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityChip
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.screens.category.CategoryItemUiState
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
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
        modifier = modifier
    ) {
        CustomBottomSheet(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Title()
                }
                item {
                    TaskTitleTextField(Modifier.fillMaxWidth())
                }
                item {
                    DescriptionTextField(Modifier.fillMaxWidth())
                }
                item {
                    DateTextField(Modifier.fillMaxWidth())
                }

                item {
                    Title(text = stringResource(R.string.priority))
                }
                item {
                    PrioritySection()
                }
                item {
                    Title(text = stringResource(R.string.category))
                }

                item {
                    CategorySection()
                }
            }
        }
        ActionButtons(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySection() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(29.dp),
        verticalArrangement = Arrangement.spacedBy(29.dp)
    ) {
        CategoryItem(
            categoryItemUiState = CategoryItemUiState(
                categoryImage = painterResource(R.drawable.book_open_icon),
                categoryName = "Education"
            )
        )
        CategoryItem(
            categoryItemUiState = CategoryItemUiState(
                categoryImage = painterResource(R.drawable.book_open_icon),
                categoryName = "Education"
            )
        )
        CategoryItem(
            categoryItemUiState = CategoryItemUiState(
                categoryImage = painterResource(R.drawable.book_open_icon),
                categoryName = "Education"
            )
        )
        CategoryItem(
            categoryItemUiState = CategoryItemUiState(
                categoryImage = painterResource(R.drawable.book_open_icon),
                categoryName = "Education"
            )
        )
        CategoryItem(
            categoryItemUiState = CategoryItemUiState(
                categoryImage = painterResource(R.drawable.book_open_icon),
                categoryName = "Education"
            )
        )
    }
}

@Composable
private fun PrioritySection() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PriorityChip(priorityUi = PriorityUi.LOW, false)

        PriorityChip(priorityUi = PriorityUi.MEDIUM, false)

        PriorityChip(priorityUi = PriorityUi.HIGH, false)
    }
}

@Composable
private fun DateTextField(
    modifier: Modifier
) {
    CustomTextField(
        text = "",
        modifier = modifier,
        style = AppTheme.textStyle.label.medium,
        hintText = "22-6-2025",
        maxLines = 1,
        leadingIcon = R.drawable.calendar_add_icon,
        borderColor = AppTheme.color.stroke,
        borderFocusedColor = AppTheme.color.primary,
        onValueChange = {}
    )
}


@Composable
private fun DescriptionTextField(
    modifier: Modifier
) {
    CustomTextField(
        hintText = stringResource(R.string.task_description_hint),
        maxLines = 5,
        borderColor = AppTheme.color.stroke,
        borderFocusedColor = AppTheme.color.primary,
        text = "",
        style = AppTheme.textStyle.label.medium,
        onValueChange = {}
    )
}

@Composable
private fun TaskTitleTextField(
    modifier: Modifier
) {
    CustomTextField(
        text = "",
        modifier = modifier,
        style = AppTheme.textStyle.label.medium,
        hintText = stringResource(R.string.task_title_hint),
        maxLines = 1,
        leadingIcon = R.drawable.note_icon,
        borderColor = AppTheme.color.stroke,
        borderFocusedColor = AppTheme.color.primary,
        onValueChange = {}
    )
}

@Composable
private fun ActionButtons(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .dropShadow(
                shape = RectangleShape,
                color = AppTheme.color.dropShadowColor,
            )
            .background(AppTheme.color.surfaceHigh),
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

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.add_task),
) {
    Text(
        text = text,
        style = AppTheme.textStyle.title.large,
        color = AppTheme.color.title,
        modifier = modifier
    )
}

@ThemeAndLocalePreviews
@Composable
private fun AddOrEditTaskScreenPreview() {
    CuteTudeeTheme {
        AddOrEditTaskScreen()
    }

}