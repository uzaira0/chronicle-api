package com.openlattice.chronicle.enums

import com.openlattice.chronicle.pipeline.PipelineRunStatus
import com.openlattice.chronicle.pipeline.PipelineStepType
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class PipelineEnumsTest {

    // ===== PipelineRunStatus (4 values) =====
    @Test fun testPipelineRunStatusCount() { assertEquals(4, PipelineRunStatus.values().size) }
    @Test fun testPipelineRunStatusPending() { assertEquals("PENDING", PipelineRunStatus.PENDING.name) }
    @Test fun testPipelineRunStatusRunning() { assertEquals("RUNNING", PipelineRunStatus.RUNNING.name) }
    @Test fun testPipelineRunStatusCompleted() { assertEquals("COMPLETED", PipelineRunStatus.COMPLETED.name) }
    @Test fun testPipelineRunStatusFailed() { assertEquals("FAILED", PipelineRunStatus.FAILED.name) }
    @Test fun testPipelineRunStatusOrdinalPending() { assertEquals(0, PipelineRunStatus.PENDING.ordinal) }
    @Test fun testPipelineRunStatusOrdinalRunning() { assertEquals(1, PipelineRunStatus.RUNNING.ordinal) }
    @Test fun testPipelineRunStatusOrdinalCompleted() { assertEquals(2, PipelineRunStatus.COMPLETED.ordinal) }
    @Test fun testPipelineRunStatusOrdinalFailed() { assertEquals(3, PipelineRunStatus.FAILED.ordinal) }
    @Test fun testPipelineRunStatusValueOfPending() { assertEquals(PipelineRunStatus.PENDING, PipelineRunStatus.valueOf("PENDING")) }
    @Test fun testPipelineRunStatusValueOfInvalid() {
        try {
            PipelineRunStatus.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== PipelineStepType (5 values) =====
    @Test fun testPipelineStepTypeCount() { assertEquals(5, PipelineStepType.values().size) }
    @Test fun testPipelineStepTypeDeidentification() { assertEquals("DEIDENTIFICATION", PipelineStepType.DEIDENTIFICATION.name) }
    @Test fun testPipelineStepTypeFeatureExtraction() { assertEquals("FEATURE_EXTRACTION", PipelineStepType.FEATURE_EXTRACTION.name) }
    @Test fun testPipelineStepTypeAggregation() { assertEquals("AGGREGATION", PipelineStepType.AGGREGATION.name) }
    @Test fun testPipelineStepTypeTimeBucketing() { assertEquals("TIME_BUCKETING", PipelineStepType.TIME_BUCKETING.name) }
    @Test fun testPipelineStepTypeCustomSql() { assertEquals("CUSTOM_SQL", PipelineStepType.CUSTOM_SQL.name) }
    @Test fun testPipelineStepTypeOrdinalDeidentification() { assertEquals(0, PipelineStepType.DEIDENTIFICATION.ordinal) }
    @Test fun testPipelineStepTypeOrdinalFeatureExtraction() { assertEquals(1, PipelineStepType.FEATURE_EXTRACTION.ordinal) }
    @Test fun testPipelineStepTypeOrdinalAggregation() { assertEquals(2, PipelineStepType.AGGREGATION.ordinal) }
    @Test fun testPipelineStepTypeOrdinalTimeBucketing() { assertEquals(3, PipelineStepType.TIME_BUCKETING.ordinal) }
    @Test fun testPipelineStepTypeOrdinalCustomSql() { assertEquals(4, PipelineStepType.CUSTOM_SQL.ordinal) }
    @Test fun testPipelineStepTypeValueOfAggregation() { assertEquals(PipelineStepType.AGGREGATION, PipelineStepType.valueOf("AGGREGATION")) }
    @Test fun testPipelineStepTypeValueOfInvalid() {
        try {
            PipelineStepType.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
