package com.amsterdam.cutetudee.presentation.base

import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.exception.*

fun Exception.mapExceptionToResourceId(): Int {
    return when (this) {
        is TaskNotFoundException -> R.string.error_task_not_found
        is NoTasksFoundPerDateException -> R.string.error_no_tasks_found_per_date
        is InvalidTaskInputException -> R.string.error_invalid_task_input
        is InvalidCategoryInputException -> R.string.error_invalid_category_input
        is NoCategoriesFoundException -> R.string.error_no_categories_found
        is CategoryNotFoundException -> R.string.error_category_not_found
        else -> R.string.error_unknown
    }
}