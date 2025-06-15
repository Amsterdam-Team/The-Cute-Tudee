package com.amsterdam.cutetudee.domain.exception

import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

open class CuteTudeeException(message: String) : Exception(message)


open class TaskException(message: String) : CuteTudeeException(message)

@OptIn(ExperimentalUuidApi::class)
class TaskNotFoundException(taskId: Uuid) : TaskException("Task not found with id: $taskId")

class NoTasksFoundPerDateException(date: LocalDate) : TaskException("No tasks found for the given date $date")

class InvalidTaskInputException() : TaskException("Invalid Task Input Exception")


open class CategoryException(message: String) : CuteTudeeException(message)

class InvalidCategoryInputException() : CategoryException("Invalid Category Input Exception")

class NoCategoriesFoundException() : CategoryException("No categories found")

@OptIn(ExperimentalUuidApi::class)
class CategoryNotFoundException(categoryId: Uuid) : TaskException("Category not found with id: $categoryId")




