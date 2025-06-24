package com.amsterdam.cutetudee.domain.model

data class TaskStatistics(
    val totalTasks: Int,
    val doneTasks: Int,
    val toDoTasks: Int,
    val inProgressTasks: Int
)
