package com.openlattice.chronicle.pipeline

public data class PipelineStep(
    val type: PipelineStepType = PipelineStepType.DEIDENTIFICATION,
    val order: Int = 0,
    val params: Map<String, String> = emptyMap(),
)
