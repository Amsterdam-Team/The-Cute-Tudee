package com.amsterdam.cutetudee.domain.exception

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

open class CuteTudeeException(message: String) : Exception(message)

open class TaskException(message: String) : CuteTudeeException(message)

@OptIn(ExperimentalUuidApi::class)
class TaskNotFoundException(taskId: Uuid) : TaskException("Task not found with id: $taskId")

class InvalidTaskInputException() : TaskException("Invalid Task Input Exception")
@OptIn(ExperimentalUuidApi::class)
class TaskAlreadyExistsException (taskId: Uuid) : TaskException("Task with this id : $taskId already exists")

open class CategoryException(message: String) : CuteTudeeException(message)

class InvalidCategoryInputException() : CategoryException("Invalid Category Input Exception")

class DeletePredefineCategoryException() : CategoryException("You can not delete the predefine categories")
@OptIn(ExperimentalUuidApi::class)
class CategoryNotFoundException(categoryId: Uuid) : CategoryException("Category not found with id: $categoryId")




