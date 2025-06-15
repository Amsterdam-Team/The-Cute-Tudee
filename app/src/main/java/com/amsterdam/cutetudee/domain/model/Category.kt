package com.amsterdam.cutetudee.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Category(
    val auditId: Uuid = Uuid.random(),
    val name: String,
    val iconUrl: String
)