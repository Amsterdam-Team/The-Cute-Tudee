package com.amsterdam.cutetudee.presentation.screens.tasks

import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.utils.getCurrentLocalDate
import com.amsterdam.cutetudee.presentation.utils.toBitmap
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AddEditTaskUiState(
    val id: String = "",
    val taskName: String = "",
    val description: String = "",
    val priority: PriorityUi? = null,
    val selectedCategoryId: String = "",
    val categories: List<CategoryItemUiState> = emptyList(),
    val status: Task.Status = Task.Status.TODO,
    val taskAction: TaskAction = TaskAction.ADD,
    val isLoading: Boolean = false,
    val isDateFilled: Boolean = false
) {
    enum class TaskAction { ADD, EDIT }
    data class CategoryItemUiState(
        val id: String,
        val name: String,
        val image: Uri
    )
}


@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryItemUiState(): AddEditTaskUiState.CategoryItemUiState =
    AddEditTaskUiState.CategoryItemUiState(
        id = id.toString(),
        name = name,
        image = image.toUri()
    )

@OptIn(ExperimentalUuidApi::class)
fun Task.toCategoryItemUiState(categories: List<Category>): AddEditTaskUiState {
    return AddEditTaskUiState(
        id = id.toString(),
        taskName = title,
        description = description ?: "",
        priority = PriorityUi.valueOf(priority.name),
        selectedCategoryId = categoryId.toString(),
        categories = categories.map { category -> category.toCategoryItemUiState() }
    )
}

@OptIn(ExperimentalUuidApi::class)
fun AddEditTaskUiState.toTask(): Task {
    val id = if (id == null || id.isEmpty()) Uuid.random() else Uuid.parse(id)
    return Task(
        id = id,
        title = taskName,
        description = description,
        targetDate = getCurrentLocalDate(),
        priority = Task.Priority.valueOf(priority?.name ?: Task.Priority.LOW.name),
        categoryId = Uuid.parse(selectedCategoryId),
        status = status
    )
}
