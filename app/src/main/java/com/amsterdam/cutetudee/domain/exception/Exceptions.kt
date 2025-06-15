package com.amsterdam.cutetudee.domain.exception

import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

open class CuteTudeeException(message: String) : Exception(message)


open class TaskExceptions(message: String) : CuteTudeeException(message)

@OptIn(ExperimentalUuidApi::class)
class TaskNotFoundException(taskId: Uuid) : TaskExceptions("Task not found with id: $taskId")

class NoTasksFoundPerDateException(date: LocalDate) : TaskExceptions("No tasks found for the given date $date")

class InvalidTaskInputException() : TaskExceptions("Invalid Task Input Exception")


open class CategoryException(message: String) : CuteTudeeException(message)

class InvalidCategoryInputException() : CategoryException("Invalid Category Input Exception")

class NoCategoriesFoundException() : CategoryException("No categories found")

@OptIn(ExperimentalUuidApi::class)
class CategoryNotFoundException(categoryId: Uuid) : TaskExceptions("Category not found with id: $categoryId")




