package com.amsterdam.cutetudee.presentation.screens.tasks

import android.net.Uri
import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.utils.DateTimeHandler
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AddEditTaskUiState(
    val id: String = "",
    val taskName: String = "",
    val description: String = "",
    val date: String = DateTimeHandler().getCurrentStringDate("EEE, MMM dd"),
    val dateInMillis: Long = DateTimeHandler().getCurrentDateInMillis(),
    val priority: PriorityUi = PriorityUi.LOW,
    val selectedCategoryId: String = "",
    val categories: List<CategoryItemUiState> = emptyList(),
    val status: Task.Status = Task.Status.TODO,
    val taskAction: TaskAction = TaskAction.ADD,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false
) {
    enum class TaskAction { ADD, EDIT }
    data class CategoryItemUiState(
        val id: String,
        val name: String,
        val image: Uri
    )
}


@OptIn(ExperimentalUuidApi::class)
fun Category.toAddEditCategoryUiState(): AddEditTaskUiState.CategoryItemUiState =
    AddEditTaskUiState.CategoryItemUiState(
        id = id.toString(),
        name = name,
        image = image.toUri()
    )

@OptIn(ExperimentalUuidApi::class)
fun Task.toAddEditTaskUiState(categories: List<Category>): AddEditTaskUiState {
    return AddEditTaskUiState(
        id = id.toString(),
        taskName = title,
        description = description ?: "",
        date = DateTimeHandler().getStringDateFromLocalDate(targetDate),
        priority = PriorityUi.valueOf(priority.name),
        selectedCategoryId = categoryId.toString(),
        categories = categories.map { category -> category.toAddEditCategoryUiState() },
        dateInMillis = DateTimeHandler().getDateInMillisFromLocalDate(targetDate),
        status = status,
        isLoading = false,
        isEnabled = false
    )
}

@OptIn(ExperimentalUuidApi::class)
fun AddEditTaskUiState.toTask(): Task {
    val id = if (id.isEmpty()) Uuid.random() else Uuid.parse(id)
    return Task(
        id = id,
        title = taskName,
        description = description,
        targetDate = DateTimeHandler().getLocalDateFromMillis(dateInMillis),
        priority = Task.Priority.valueOf(priority?.name ?: Task.Priority.LOW.name),
        categoryId = Uuid.parse(selectedCategoryId),
        status = status
    )
}
