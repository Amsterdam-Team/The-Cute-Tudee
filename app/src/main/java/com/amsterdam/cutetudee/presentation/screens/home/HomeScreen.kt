package com.amsterdam.cutetudee.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.component.CustomFloatingActionButton
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.custom_snack_bar.CustomSnackBarStatus
import com.amsterdam.cutetudee.presentation.component.task_card.TaskItemUiState
import com.amsterdam.cutetudee.presentation.screens.home.component.OverlayBoxContent
import com.amsterdam.cutetudee.presentation.screens.home.component.TaskSection
import com.amsterdam.cutetudee.presentation.screens.home.component.TopCuteTudeeAppBar
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun HomeScreen(
    navController: NavController,
    onShowSnackBar: (message: String, status: CustomSnackBarStatus) -> Unit
) {
    HomeScreenContent()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    Box() {
        CustomFloatingActionButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 84.dp, end = 12.dp)
                .zIndex(10f),
            onClick = { /*TODO*/ },
            isLoading = false,
            iconDrawable = painterResource(id = R.drawable.note_add_icon),
            isEnabled = true,
            iconDescription = "Add Task"
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopCuteTudeeAppBar(
                title = "Tudee", description = "Your cute Helper for Every Task", changeTheme = {})
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(AppTheme.color.surface),
                contentPadding = PaddingValues(bottom = 82.dp)
            ) {
                item {
                    OverlayBoxContent()
                }
                item {
                    TaskSection(
                        title = "In Progress", tasks = listOf(
                            TaskItemUiState(
                                categoryImage = painterResource(R.drawable.quran_icon),
                                priorityUi = PriorityUi.HIGH,
                                name = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow"
                            ), TaskItemUiState(
                                categoryImage = painterResource(R.drawable.book_open_icon),
                                priorityUi = PriorityUi.MEDIUM,
                                name = "Organize Study Desk"
                            )
                        )
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    TaskSection(
                        title = "To-Do", tasks = listOf(
                            TaskItemUiState(
                                categoryImage = painterResource(R.drawable.user_multiple_icon),
                                priorityUi = PriorityUi.LOW,
                                name = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow"
                            ), TaskItemUiState(
                                categoryImage = painterResource(R.drawable.airplane_icon),
                                priorityUi = PriorityUi.MEDIUM,
                                name = "Organize Study Desk"
                            ), TaskItemUiState(
                                categoryImage = painterResource(R.drawable.airplane_icon),
                                priorityUi = PriorityUi.MEDIUM,
                                name = "Organize Study Desk"
                            )
                        )
                    )

                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    TaskSection(
                        title = "Done", tasks = listOf(
                            TaskItemUiState(
                                categoryImage = painterResource(R.drawable.user_multiple_icon),
                                priorityUi = PriorityUi.LOW,
                                name = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow"
                            ), TaskItemUiState(
                                categoryImage = painterResource(R.drawable.airplane_icon),
                                priorityUi = PriorityUi.MEDIUM,
                                name = "Organize Study Desk"
                            )
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}