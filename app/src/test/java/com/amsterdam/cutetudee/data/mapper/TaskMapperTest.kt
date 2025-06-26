package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.data.local.dto.TaskDto
import com.amsterdam.cutetudee.domain.entity.Task
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TaskMapperTest {
    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should map Task to TaskEntity`() {
        val task = Task(
            id = Uuid.random(),
            title = "Test Task",
            description = "desc",
            targetDate = LocalDate(2024, 6, 1),
            priority = Task.Priority.HIGH,
            status = Task.Status.DONE,
            categoryId = Uuid.random()
        )
        val entity = task.toTaskEntity()
        assertEquals(task.id.toString(), entity.id)
        assertEquals(task.title, entity.title)
        assertEquals(task.description, entity.description)
        assertEquals(task.targetDate.toEpochDays().toLong(), entity.targetDate)
        assertEquals(task.priority.ordinal, entity.priority)
        assertEquals(task.categoryId.toString(), entity.categoryId)
        assertEquals(task.status.ordinal, entity.status)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should map TaskEntity to Task`() {
        val uuid = Uuid.random()
        val catId = Uuid.random()
        val entity = TaskDto(
            id = uuid.toString(),
            title = "Task",
            description = "desc",
            targetDate = 20000L,
            priority = 2,
            categoryId = catId.toString(),
            status = 1
        )
        val task = entity.toTask()
        assertEquals(entity.id, task.id.toString())
        assertEquals(entity.title, task.title)
        assertEquals(entity.description, task.description)
        assertEquals(entity.targetDate.toInt(), task.targetDate.toEpochDays())
        assertEquals(entity.priority, task.priority.ordinal)
        assertEquals(entity.categoryId, task.categoryId.toString())
        assertEquals(entity.status, task.status.ordinal)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should map Flow List of TaskEntity  to Flow List of Task`() = runTest {
        val uuid = Uuid.random()
        val catId = Uuid.random()
        val entity = TaskDto(
            id = uuid.toString(),
            title = "Task",
            description = "desc",
            targetDate = 20000L,
            priority = 2,
            categoryId = catId.toString(),
            status = 1
        )
        val flow = flowOf(listOf(entity))
        val mappedFlow = flow.toTaskListFlow()
        mappedFlow.collect { list ->
            assertEquals(1, list.size)
            val task = list[0]
            assertEquals(entity.id, task.id.toString())
        }
    }

    @Test
    fun `should convert LocalDate to Long and back`() {
        val date = LocalDate(2024, 6, 1)
        val long = date.toLong()
        val dateBack = long.toLocalDate()
        assertEquals(date, dateBack)
    }
} 