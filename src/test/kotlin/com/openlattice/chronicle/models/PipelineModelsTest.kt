package com.openlattice.chronicle.models

import com.openlattice.chronicle.pipeline.PipelineConfig
import com.openlattice.chronicle.pipeline.PipelineRunInfo
import com.openlattice.chronicle.pipeline.PipelineRunStatus
import com.openlattice.chronicle.pipeline.PipelineStep
import com.openlattice.chronicle.pipeline.PipelineStepType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class PipelineModelsTest {

    // ===== PipelineStep =====
    @Test fun testPipelineStepDefaultType() { assertEquals(PipelineStepType.DEIDENTIFICATION, PipelineStep().type) }
    @Test fun testPipelineStepDefaultOrder() { assertEquals(0, PipelineStep().order) }
    @Test fun testPipelineStepDefaultParams() { assertTrue(PipelineStep().params.isEmpty()) }
    @Test fun testPipelineStepCustomType() {
        val step = PipelineStep(type = PipelineStepType.AGGREGATION)
        assertEquals(PipelineStepType.AGGREGATION, step.type)
    }
    @Test fun testPipelineStepCustomOrder() {
        val step = PipelineStep(order = 5)
        assertEquals(5, step.order)
    }
    @Test fun testPipelineStepCustomParams() {
        val step = PipelineStep(params = mapOf("key" to "value"))
        assertEquals(1, step.params.size)
        assertEquals("value", step.params["key"])
    }
    @Test fun testPipelineStepEquality() {
        assertEquals(PipelineStep(), PipelineStep())
    }
    @Test fun testPipelineStepInequality() {
        assertNotEquals(PipelineStep(order = 0), PipelineStep(order = 1))
    }
    @Test fun testPipelineStepHashCode() {
        assertEquals(PipelineStep().hashCode(), PipelineStep().hashCode())
    }
    @Test fun testPipelineStepCopy() {
        val s = PipelineStep(type = PipelineStepType.CUSTOM_SQL, order = 3)
        val c = s.copy(order = 4)
        assertEquals(PipelineStepType.CUSTOM_SQL, c.type)
        assertEquals(4, c.order)
    }
    @Test fun testPipelineStepToString() { assertNotNull(PipelineStep().toString()) }

    // ===== PipelineConfig =====
    @Test fun testPipelineConfigDefaultSteps() {
        val config = PipelineConfig()
        assertEquals(2, config.steps.size)
    }
    @Test fun testPipelineConfigDefaultFirstStep() {
        val config = PipelineConfig()
        assertEquals(PipelineStepType.DEIDENTIFICATION, config.steps[0].type)
        assertEquals(0, config.steps[0].order)
    }
    @Test fun testPipelineConfigDefaultSecondStep() {
        val config = PipelineConfig()
        assertEquals(PipelineStepType.AGGREGATION, config.steps[1].type)
        assertEquals(1, config.steps[1].order)
    }
    @Test fun testPipelineConfigDefaultOutputTable() { assertEquals("preprocessed_usage_events", PipelineConfig().outputTable) }
    @Test fun testPipelineConfigDefaultTimeBucketMinutes() { assertEquals(60, PipelineConfig().timeBucketMinutes) }
    @Test fun testPipelineConfigDefaultEnabled() { assertFalse(PipelineConfig().enabled) }
    @Test fun testPipelineConfigCustomEnabled() {
        val config = PipelineConfig(enabled = true)
        assertTrue(config.enabled)
    }
    @Test fun testPipelineConfigCustomOutputTable() {
        val config = PipelineConfig(outputTable = "custom_table")
        assertEquals("custom_table", config.outputTable)
    }
    @Test fun testPipelineConfigCustomTimeBucket() {
        val config = PipelineConfig(timeBucketMinutes = 15)
        assertEquals(15, config.timeBucketMinutes)
    }
    @Test fun testPipelineConfigEquality() {
        assertEquals(PipelineConfig(), PipelineConfig())
    }
    @Test fun testPipelineConfigToString() { assertNotNull(PipelineConfig().toString()) }

    // ===== PipelineRunInfo =====
    @Test fun testPipelineRunInfoConstruction() {
        val runId = UUID.randomUUID()
        val studyId = UUID.randomUUID()
        val info = PipelineRunInfo(runId = runId, studyId = studyId)
        assertEquals(runId, info.runId)
        assertEquals(studyId, info.studyId)
    }
    @Test fun testPipelineRunInfoDefaultStatus() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertEquals(PipelineRunStatus.PENDING, info.status)
    }
    @Test fun testPipelineRunInfoDefaultStepsCompleted() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertEquals(0, info.stepsCompleted)
    }
    @Test fun testPipelineRunInfoDefaultTotalSteps() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertEquals(0, info.totalSteps)
    }
    @Test fun testPipelineRunInfoDefaultInputRows() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertEquals(0L, info.inputRows)
    }
    @Test fun testPipelineRunInfoDefaultOutputRows() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertEquals(0L, info.outputRows)
    }
    @Test fun testPipelineRunInfoStartedAtNotNull() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertNotNull(info.startedAt)
    }
    @Test fun testPipelineRunInfoDefaultCompletedAt() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertNull(info.completedAt)
    }
    @Test fun testPipelineRunInfoDefaultErrorMessage() {
        val info = PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID())
        assertNull(info.errorMessage)
    }
    @Test fun testPipelineRunInfoCustomValues() {
        val info = PipelineRunInfo(
            UUID.randomUUID(), UUID.randomUUID(),
            status = PipelineRunStatus.COMPLETED,
            stepsCompleted = 3,
            totalSteps = 3,
            inputRows = 1000,
            outputRows = 500
        )
        assertEquals(PipelineRunStatus.COMPLETED, info.status)
        assertEquals(3, info.stepsCompleted)
        assertEquals(3, info.totalSteps)
        assertEquals(1000L, info.inputRows)
        assertEquals(500L, info.outputRows)
    }
    @Test fun testPipelineRunInfoToString() {
        assertNotNull(PipelineRunInfo(UUID.randomUUID(), UUID.randomUUID()).toString())
    }
}
