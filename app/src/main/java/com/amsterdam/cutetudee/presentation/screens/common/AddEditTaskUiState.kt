package com.amsterdam.cutetudee.presentation.screens.common

import android.net.Uri
import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.utils.getCurrentDateInMillis
import com.amsterdam.cutetudee.presentation.utils.getCurrentStringDate
import com.amsterdam.cutetudee.presentation.utils.getLocalDateFromMillis
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AddEditTaskUiState(
    val id: String = "",
    val taskName: String = "",
    val description: String = "",
    val date: String = getCurrentStringDate(),
    val dateInMillis: Long = getCurrentDateInMillis(),
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
fun AddEditTaskUiState.toTask(): Task {
    val id = if (id.isEmpty()) Uuid.random() else Uuid.parse(id)
    return Task(
        id = id,
        title = taskName,
        description = description,
        targetDate = dateInMillis.getLocalDateFromMillis(),
        priority = Task.Priority.valueOf(priority.name),
        categoryId = Uuid.parse(selectedCategoryId),
        status = status
    )
}
