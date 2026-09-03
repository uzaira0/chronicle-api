package com.openlattice.chronicle.models

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class AndroidCoreApiContractTest {
    private val specification: String by lazy { File("chronicle.yaml").readText() }

    @Test
    fun `v4 publishes the three core Android upload routes`() {
        listOf(
            "/chronicle/v4/study/{studyId}/participant/{participantId}/android:" to
                "uploadAndroidUsageEventDataV4",
            "/chronicle/v4/study/{studyId}/participant/{participantId}/android/sensors:" to
                "uploadAndroidSensorDataV4",
            "/chronicle/v4/study/{studyId}/participant/{participantId}/android/battery:" to
                "uploadBatteryTelemetryV4",
        ).forEach { (path, operationId) ->
            val operation = specification.substringAfter("  $path")
                .substringBefore("\n  /chronicle/")

            assertTrue(operation.contains("operationId: $operationId"))
            assertTrue(operation.contains("#/components/parameters/chronicleDeviceIdHeader"))
            assertTrue(operation.contains("#/components/parameters/mobileApiKeyHeader"))
        }
    }

    @Test
    fun `participant request matches the runtime model`() {
        val participantSchema = specification.substringAfter("    Participant:")
            .substringBefore("    ParticipationStatus:")

        assertTrue(participantSchema.contains("- participantId"))
        assertTrue(participantSchema.contains("- candidate"))
        assertTrue(participantSchema.contains("- participationStatus"))
        assertTrue(participantSchema.contains("candidate:"))
        assertTrue(participantSchema.contains("participantNotes:"))
        assertTrue(participantSchema.contains("participantTags:"))
        assertFalse(participantSchema.contains("candidateId:"))
        assertFalse(participantSchema.contains("annotations:"))
    }

    @Test
    fun `collection acknowledgment publishes unavailable sensor modules`() {
        val acknowledgmentSchema = specification.substringAfter("    CollectionAcknowledgment:")
            .substringBefore("    CollectionAcknowledgmentEntry:")
        val acknowledgmentOperation = specification
            .substringAfter("  /chronicle/v4/study/{studyId}/participant/{participantId}/android/collection-ack:")
            .substringBefore("\n  /chronicle/")

        assertTrue(acknowledgmentSchema.contains("unavailableModules:"))
        assertTrue(acknowledgmentSchema.contains("Non-sensor modules are invalid here"))
        assertTrue(acknowledgmentOperation.contains("#/components/parameters/chronicleDeviceIdHeader"))
        assertTrue(acknowledgmentOperation.contains("#/components/parameters/mobileApiKeyHeader"))
    }
}
