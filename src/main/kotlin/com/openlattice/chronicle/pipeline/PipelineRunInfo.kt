package com.openlattice.chronicle.pipeline

import java.time.OffsetDateTime
import java.util.*

public data class PipelineRunInfo(
    val runId: UUID,
    val studyId: UUID,
    val status: PipelineRunStatus = PipelineRunStatus.PENDING,
    val stepsCompleted: Int = 0,
    val totalSteps: Int = 0,
    val inputRows: Long = 0,
    val outputRows: Long = 0,
    val startedAt: OffsetDateTime = OffsetDateTime.now(),
    val completedAt: OffsetDateTime? = null,
    val errorMessage: String? = null,
)
