package com.openlattice.chronicle.pipeline

import com.openlattice.chronicle.study.StudySetting

public data class PipelineConfig(
    val steps: List<PipelineStep> = listOf(
        PipelineStep(type = PipelineStepType.DEIDENTIFICATION, order = 0),
        PipelineStep(type = PipelineStepType.AGGREGATION, order = 1),
    ),
    val outputTable: String = "preprocessed_usage_events",
    val timeBucketMinutes: Int = 60,
    val enabled: Boolean = false,
) : StudySetting
