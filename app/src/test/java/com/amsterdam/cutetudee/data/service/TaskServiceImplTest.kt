package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.entity.TaskEntity
import com.amsterdam.cutetudee.data.mapper.toTask
import com.amsterdam.cutetudee.domain.model.Task
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TaskServiceImplTest {
    private lateinit var taskDao: TaskDao
    private lateinit var tasksServices: TaskServiceImpl
    private val sampleTaskEntity = TaskEntity(
        id = "7d27d7bd-e8a0-4c48-9d6a-fab968df48d2",
        title = "Task 1",
        description = "Task 2 is task 1 and more than 1",
        targetDate = 20252506,
        priority = 1,
        categoryId = "7d27d7bd-e8a0-4c48-9d6a-fab968df48d3",
        status = 1
    )

    @OptIn(ExperimentalUuidApi::class)
    private val sampleTaskEntityId = Uuid.parse(sampleTaskEntity.id)

    @BeforeEach
    fun setUp() {
        taskDao = mockk(relaxed = true)

        tasksServices = TaskServiceImpl(taskDao)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should return task when task exist`() = runTest {
        coEvery { taskDao.getTaskById(any()) } returns sampleTaskEntity

        val result = tasksServices.getTaskById(sampleTaskEntityId)

        assertEquals(sampleTaskEntity.id, result.id.toString())
        coVerify(exactly = 1) { taskDao.getTaskById(any()) }
    }

    @Test
    fun `should add task by calling upsertTask`() = runTest {
        coEvery { taskDao.upsertTask(any()) } returns Unit
        tasksServices.addTask(sampleTaskEntity.toTask())
        coVerify { taskDao.upsertTask(any()) }
    }

    @Test
    fun `should edit task by calling upsertTask`() = runTest {
        coEvery { taskDao.upsertTask(any()) } returns Unit
        tasksServices.editTask(sampleTaskEntity.toTask())
        coVerify { taskDao.upsertTask(any()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should delete task by calling deleteTask`() = runTest {
        tasksServices.deleteTask(taskId = sampleTaskEntityId)
        coVerify(exactly = 1) { tasksServices.deleteTask(sampleTaskEntityId) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should throw when getTaskById returns null`() = runTest {
        val notExistedId = Uuid.random()
        coEvery { taskDao.getTaskById(any()) } throws IllegalStateException()
        assertThrows<IllegalStateException> {
            tasksServices.getTaskById(notExistedId)
        }
        coVerify(exactly = 1) { taskDao.getTaskById(any()) }
    }

    @Test
    fun `getTasksByDate should return flow from dao`() {
        val date = kotlinx.datetime.LocalDate(2024, 6, 1)
        val flow = mockk<Flow<List<TaskEntity>>>()
        coEvery { taskDao.getTaskByDate(any()) } returns flow
        val result = tasksServices.getTasksByDate(date)
        assertNotNull(result)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByCategoryId should return flow from dao`() {
        val uuid = sampleTaskEntityId
        val flow = mockk<Flow<List<TaskEntity>>>()
        coEvery { taskDao.getTasksByCategoryId(uuid.toString()) } returns flow
        val result = tasksServices.getTasksByCategoryId(uuid)
        assertThat(result).isNotNull()
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByCategoryIdAndStatus should return flow from dao`() {
        val uuid = sampleTaskEntityId
        val flow = mockk<Flow<List<TaskEntity>>>()
        coEvery { taskDao.getTasksByCategoryIdAndStatus(uuid.toString(), 1) } returns flow
        val result = tasksServices.getTasksByCategoryIdAndStatus(
            uuid,
            Task.Status.IN_PROGRESS
        )
        assertThat(result).isNotNull()
    }

    @Test
    fun `getTaskStatisticsByDate should return correct statistics`() = runTest {
        val date = kotlinx.datetime.LocalDate(2024, 6, 1)
        val totalFlow = flowOf(5)
        val doneFlow = flowOf(2)
        val todoFlow = flowOf(2)
        val inProgressFlow = flowOf(1)
        coEvery { taskDao.getTotalTaskCountByDate(any()) } returns totalFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(2, any()) } returns doneFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(0, any()) } returns todoFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(1, any()) } returns inProgressFlow
        val resultFlow = tasksServices.getTaskStatisticsByDate(date)
        resultFlow.collect { stats ->
            assertEquals(5, stats.totalTasks)
            assertEquals(2, stats.doneTasks)
            assertEquals(2, stats.toDoTasks)
            assertEquals(1, stats.inProgressTasks)
        }
    }

    @Test
    fun `getTaskStatisticsByDate should return zero statistics when no tasks`() = runTest {
        val date = kotlinx.datetime.LocalDate(2024, 6, 1)
        val zeroFlow = flowOf(0)
        coEvery { taskDao.getTotalTaskCountByDate(any()) } returns zeroFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(any(), any()) } returns zeroFlow
        val resultFlow = tasksServices.getTaskStatisticsByDate(date)
        resultFlow.collect { stats ->
            assertEquals(0, stats.totalTasks)
            assertEquals(0, stats.doneTasks)
            assertEquals(0, stats.toDoTasks)
            assertEquals(0, stats.inProgressTasks)
        }
    }
}
