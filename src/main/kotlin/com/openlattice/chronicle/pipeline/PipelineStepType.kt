package com.openlattice.chronicle.pipeline

public enum class PipelineStepType {
    DEIDENTIFICATION,
    FEATURE_EXTRACTION,
    AGGREGATION,
    TIME_BUCKETING,
    CUSTOM_SQL,
}
