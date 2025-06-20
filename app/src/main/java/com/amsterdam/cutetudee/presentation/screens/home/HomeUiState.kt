package com.amsterdam.cutetudee.presentation.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi

data class HomeUiState(
    val currentDate: String = "",
    val inProgressTasks: List<TaskDetails> = emptyList(),
    val todoTasks: List<TaskDetails> = emptyList(),
    val doneTasks: List<TaskDetails> = emptyList(),
    val toDoTasksNumber: Int = 0,
    val inProgressTasksNumber: Int = 0,
    val doneTasksNumber: Int = 0,
    val totalTasksNumber: Int = 0,
    val moodState: MoodState = MoodState.STAY_WORKING,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
) {
    data class TaskDetails(
        val icon: String = "",
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
    @DrawableRes val pinter: Int
) {
    STAY_WORKING(
        description = R.string.update_neutral_description,
        title = R.string.update_neutral_title,
        icon = R.drawable.okay_emoji_icon,
        pinter = R.drawable.tudee_image_neutral
    ),
    TADOO(
        description = R.string.update_happy_description,
        title = R.string.update_happy_title,
        icon = R.drawable.good_emoji_icon,
        pinter = R.drawable.tudee_image_happy
    ),
    ZERO_PROGRESS(
        description = R.string.update_angry_description,
        title = R.string.update_angry_title,
        icon = R.drawable.bad_emoji_icon,
        pinter = R.drawable.tudee_image_sad
    ),
    NOTHING_IN_YOUR_LIST(
        description = R.string.update_sad_description,
        title = R.string.update_sad_title,
        icon = R.drawable.poor_emoji_icon,
        pinter = R.drawable.tudee_image_sad
    )
}