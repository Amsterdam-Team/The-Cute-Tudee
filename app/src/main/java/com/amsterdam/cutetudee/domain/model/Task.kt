package com.amsterdam.cutetudee.domain.model

import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Task(
    val id: Uuid = Uuid.random(),
    val title: String,
    val description: String?,
    val targetDate: LocalDate,
    val priority: Priority,
    val status: Status = Status.TODO,
    val categoryId: Uuid
) {
    enum class Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    enum class Status {
        TODO,
        IN_PROGRESS,
        DONE,
    }
}