package com.amsterdam.cutetudee.data.service

import androidx.room.PrimaryKey
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.entity.TaskEntity
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.toUuid
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi

class TaskServiceImplTest {
    private lateinit var taskDao: TaskDao
    private lateinit var tasksServices: TaskServiceImpl
    private val sampleTaskEntity = TaskEntity(
        id = "2",
        title = "Task 1",
        description = "Task 2 is task 1 and more than 1",
        targetDate = 20252506,
        priority = 1,
        categoryId = "3",
        status = 1
    )

    @BeforeEach
    fun setUp() {
        taskDao = mockk(relaxed = true)

        tasksServices = TaskServiceImpl(taskDao)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should return task when task exist`() = runTest {
        val taskEntity = sampleTaskEntity.copy(
            id = "3",
            title = "Task 2",
            description = "Task 2 is task 2 and more than 1",
            targetDate = 20252506,
            priority = 1,
            categoryId = "6",
            status = 1
        )
        val expectedTask = TaskEntity(
            id = "3",
            "Task 2",
            description = "Task 2 is task 2 and more than 1",
            targetDate = 20252506,
            priority = 1,
            categoryId = "6",
            status = 1
        )

        coEvery { taskDao.getTaskById("3") } returns taskEntity

        val result = tasksServices.getTaskById("3".toUuid())


        assertEquals(expectedTask.id, result.id)
        coVerify { taskDao.getTaskById(any()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should add task by calling upsertTask`() = runTest {
        val task = mockk<com.amsterdam.cutetudee.domain.model.Task>(relaxed = true)
        coEvery { taskDao.upsertTask(any()) } returns Unit
        tasksServices.addTask(task)
        coVerify { taskDao.upsertTask(any()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should edit task by calling upsertTask`() = runTest {
        val task = mockk<com.amsterdam.cutetudee.domain.model.Task>(relaxed = true)
        coEvery { taskDao.upsertTask(any()) } returns Unit
        tasksServices.editTask(task)
        coVerify { taskDao.upsertTask(any()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should delete task by calling deleteTask`() = runTest {
        val uuid = "1234-5678-9012-3456".toUuid()
        coEvery { taskDao.deleteTask(uuid.toString()) } returns Unit
        tasksServices.deleteTask(uuid)
        coVerify { taskDao.deleteTask(uuid.toString()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should throw when getTaskById returns null`() = runTest {
        val uuid = "not-exist-id".toUuid()
        coEvery { taskDao.getTaskById(uuid.toString()) } throws NoSuchElementException()
        assertThrows(NoSuchElementException::class.java) {
            runTest { tasksServices.getTaskById(uuid) }
        }
        coVerify { taskDao.getTaskById(uuid.toString()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByDate should return flow from dao`() {
        val date = kotlinx.datetime.LocalDate(2024, 6, 1)
        val flow = mockk<kotlinx.coroutines.flow.Flow<List<TaskEntity>>>()
        coEvery { taskDao.getTaskByDate(any()) } returns flow
        val result = tasksServices.getTasksByDate(date)
        assertNotNull(result)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByCategoryId should return flow from dao`() {
        val uuid = "cat-id-1".toUuid()
        val flow = mockk<kotlinx.coroutines.flow.Flow<List<TaskEntity>>>()
        coEvery { taskDao.getTasksByCategoryId(uuid.toString()) } returns flow
        val result = tasksServices.getTasksByCategoryId(uuid)
        assertNotNull(result)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTasksByCategoryIdAndStatus should return flow from dao`() {
        val uuid = "cat-id-2".toUuid()
        val flow = mockk<kotlinx.coroutines.flow.Flow<List<TaskEntity>>>()
        coEvery { taskDao.getTasksByCategoryIdAndStatus(uuid.toString(), 1) } returns flow
        val result = tasksServices.getTasksByCategoryIdAndStatus(uuid, com.amsterdam.cutetudee.domain.model.Task.Status.IN_PROGRESS)
        assertNotNull(result)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTaskStatisticsByDate should return correct statistics`() = runTest {
        val date = kotlinx.datetime.LocalDate(2024, 6, 1)
        val totalFlow = kotlinx.coroutines.flow.flowOf(5)
        val doneFlow = kotlinx.coroutines.flow.flowOf(2)
        val todoFlow = kotlinx.coroutines.flow.flowOf(2)
        val inProgressFlow = kotlinx.coroutines.flow.flowOf(1)
        coEvery { taskDao.getTotalTaskCountByDate(any()) } returns totalFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(2, any()) } returns doneFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(0, any()) } returns todoFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(1, any()) } returns inProgressFlow
        val resultFlow = tasksServices.getTaskStatisticsByDate(date)
        kotlinx.coroutines.test.runTest {
            resultFlow.collect { stats ->
                assertEquals(5, stats.totalTasks)
                assertEquals(2, stats.doneTasks)
                assertEquals(2, stats.toDoTasks)
                assertEquals(1, stats.inProgressTasks)
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `getTaskStatisticsByDate should return zero statistics when no tasks`() = runTest {
        val date = kotlinx.datetime.LocalDate(2024, 6, 1)
        val zeroFlow = kotlinx.coroutines.flow.flowOf(0)
        coEvery { taskDao.getTotalTaskCountByDate(any()) } returns zeroFlow
        coEvery { taskDao.getTaskCountByStatusAndDate(any(), any()) } returns zeroFlow
        val resultFlow = tasksServices.getTaskStatisticsByDate(date)
        kotlinx.coroutines.test.runTest {
            resultFlow.collect { stats ->
                assertEquals(0, stats.totalTasks)
                assertEquals(0, stats.doneTasks)
                assertEquals(0, stats.toDoTasks)
                assertEquals(0, stats.inProgressTasks)
            }
        }
    }
}
