package com.amsterdam.cutetudee.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.component.task_card.TaskItemCard
import com.amsterdam.cutetudee.presentation.component.task_card.TaskItemUiState

@Composable
fun TaskSection(
    title: String, tasks: List<TaskItemUiState>
) {
    if (tasks.isEmpty()) return

    TextLabelTaskProgress(
        label = title,
        numbersOfItems = tasks.size,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
    ) {}
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { taskItem ->
            Column(modifier = Modifier.fillMaxWidth()) {
                TaskItemCard(
                    taskItemUiState = taskItem,
                    modifier = Modifier
                        .fillParentMaxWidth(0.95f)
                        .padding(bottom = 8.dp)
                )
                TaskItemCard(
                    taskItemUiState = taskItem,
                    modifier = Modifier
                        .fillParentMaxWidth(0.98f)
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

//@Composable
//private fun InProgressSection(modifier: Modifier = Modifier) {
//    TextLabelTaskProgress(
//        label = "In progress ",
//        numbersOfItems = 12,
//        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
//    ) {}
//    LazyRow(
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(5) {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                TaskItemCard(
//                    taskItemUiState = TaskItemUiState(
//                        categoryImage = painterResource(R.drawable.quran_icon),
//                        priorityUi = PriorityUi.HIGH,
//                        name = "Organize Study Desk",
//                        description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow",
//                    ), modifier = Modifier
//                        .fillParentMaxWidth(0.95f)
//                        .padding(bottom = 8.dp)
//                )
//                TaskItemCard(
//                    taskItemUiState = TaskItemUiState(
//                        categoryImage = painterResource(R.drawable.book_open_icon),
//                        priorityUi = PriorityUi.MEDIUM,
//                        name = "Organize Study Desk",
//                    ), modifier = Modifier
//                        .fillParentMaxWidth(0.98f)
//                        .padding(bottom = 8.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun ToDoSection(modifier: Modifier = Modifier) {
//    TextLabelTaskProgress(
//        label = "To-Do",
//        numbersOfItems = 12,
//        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
//    ) {}
//    LazyRow(
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(5) {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                TaskItemCard(
//                    taskItemUiState = TaskItemUiState(
//                        categoryImage = painterResource(R.drawable.user_multiple_icon),
//                        priorityUi = PriorityUi.LOW,
//                        name = "Organize Study Desk",
//                        description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow",
//                    ), modifier = Modifier
//                        .fillParentMaxWidth(0.98f)
//                        .padding(bottom = 8.dp)
//                )
//                TaskItemCard(
//                    taskItemUiState = TaskItemUiState(
//                        categoryImage = painterResource(R.drawable.airplane_icon),
//                        priorityUi = PriorityUi.MEDIUM,
//                        name = "Organize Study Desk",
//                    ), modifier = Modifier
//                        .fillParentMaxWidth(0.98f)
//                        .padding(bottom = 8.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun DoneSection(modifier: Modifier = Modifier) {
//    TextLabelTaskProgress(
//        label = "Done",
//        numbersOfItems = 12,
//        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
//    ) { }
//    LazyRow(
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(5) {
//            Column(modifier = Modifier.fillMaxWidth()) {
//                TaskItemCard(
//                    taskItemUiState = TaskItemUiState(
//                        categoryImage = painterResource(R.drawable.user_multiple_icon),
//                        priorityUi = PriorityUi.LOW,
//                        name = "Organize Study Desk",
//                        description = "Review cell structure and functions for tomorrow, Review cell structure and functions for tomorrow",
//                    ), modifier = Modifier
//                        .fillParentMaxWidth(0.98f)
//                        .padding(bottom = 8.dp)
//                )
//                TaskItemCard(
//                    taskItemUiState = TaskItemUiState(
//                        categoryImage = painterResource(R.drawable.airplane_icon),
//                        priorityUi = PriorityUi.MEDIUM,
//                        name = "Organize Study Desk",
//                    ), modifier = Modifier
//                        .fillParentMaxWidth(0.98f)
//                        .padding(bottom = 8.dp)
//                )
//            }
//        }
//    }
//}