package com.amsterdam.cutetudee.domain.entity

data class TaskStatistics(
    val totalTasks: Int,
    val doneTasks: Int,
    val toDoTasks: Int,
    val inProgressTasks: Int
)
