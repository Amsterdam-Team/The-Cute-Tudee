package com.amsterdam.cutetudee.data.local.entity

import com.amsterdam.cutetudee.domain.model.Task.Priority
import kotlinx.datetime.LocalDate

data class TaskEntity(
    val auditId: String,
    val title: String,
    val description: String?,
    val targetDate: LocalDate,
    val priority: Priority,
    val categoryId: String
)
