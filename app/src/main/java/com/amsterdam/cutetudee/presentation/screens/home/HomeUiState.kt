package com.amsterdam.cutetudee.presentation.screens.home

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeUiState(
    val currentDate: LocalDate =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date,
    val inProgressTasks: List<TaskDetails> = emptyList(),
    val todoTasks: List<TaskDetails> = emptyList(),
    val doneTasks: List<TaskDetails> = emptyList(),
    val toDoTasksNumber: Int = 0,
    val inProgressTasksNumber: Int = 0,
    val doneTasksNumber: Int = 0,
    val totalTasksNumber: Int = 0,
    val moodState: MoodState = MoodState.STAY_WORKING,
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
    val showAddTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val selectedTask: TaskUi? = null,
    @StringRes val errorMessageId: Int? = null,
) {
    data class TaskDetails(
        val icon: Uri = Uri.EMPTY,
        val title: String = "",
        val description: String = "",
        val taskState: Task.Status = Task.Status.TODO,
        val taskPriority: PriorityUi = PriorityUi.LOW,
    )
}

enum class MoodState(
    @StringRes val description: Int,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val pinter: Int,
) {
    STAY_WORKING(
        description = R.string.update_neutral_description,
        title = R.string.update_neutral_title,
        icon = R.drawable.okay_emoji_icon,
        pinter = R.drawable.tudee_image_neutral,
    ),
    TADAA(
        description = R.string.update_happy_description,
        title = R.string.update_happy_title,
        icon = R.drawable.good_emoji_icon,
        pinter = R.drawable.tudee_image_happy,
    ),
    ZERO_PROGRESS(
        description = R.string.update_angry_description,
        title = R.string.update_angry_title,
        icon = R.drawable.bad_emoji_icon,
        pinter = R.drawable.tudee_image_sad,
    ),
    NOTHING_IN_YOUR_LIST(
        description = R.string.update_sad_description,
        title = R.string.update_sad_title,
        icon = R.drawable.poor_emoji_icon,
        pinter = R.drawable.tudee_image_neutral,
    ),
}
