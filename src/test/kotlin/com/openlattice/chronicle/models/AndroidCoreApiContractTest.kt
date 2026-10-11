package com.openlattice.chronicle.models

import com.openlattice.chronicle.ChronicleTestUtils.JSON_MIME_TYPE
import com.openlattice.chronicle.ChronicleTestUtils.createHttpClient
import com.openlattice.chronicle.ChronicleTestUtils.createRetrofitAdapter
import com.openlattice.chronicle.ChronicleTestUtils.readBody
import com.openlattice.chronicle.android.ChronicleData
import com.openlattice.chronicle.android.AndroidSensorSample
import com.openlattice.chronicle.android.AndroidSensorType
import com.openlattice.chronicle.android.SensorUploadReceipt
import com.openlattice.chronicle.collection.CollectionUploadEvidence
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.util.RetrofitBuilders.mapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.util.UUID
import java.time.OffsetDateTime

class AndroidCoreApiContractTest {
    private val specification: String by lazy { File("chronicle.yaml").readText() }

    @Test
    fun `sensor receipts transmit capture evidence and decode all row dispositions`() {
        val study = UUID.randomUUID()
        val samples = List(3) { AndroidSensorSample(UUID.randomUUID(), AndroidSensorType.accelerometer, OffsetDateTime.now(), "UTC") }
        val expected = SensorUploadReceipt(setOf(samples[0].id), setOf(samples[1].id), setOf(samples[2].id), 2)
        val api = createRetrofitAdapter(createHttpClient({ chain ->
            val request = chain.request()
            assertEquals("/chronicle/v4/study/$study/participant/participant/android/sensors/receipt", request.url.encodedPath)
            assertEquals("device", request.header("X-Chronicle-Device-Id"))
            assertEquals("1", request.header(CollectionUploadEvidence.SETTINGS_VERSION_HEADER))
            assertEquals(mapper.writeValueAsString(samples), readBody(request.body).toString(Charsets.UTF_8))
            Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                .header("Content-Type", JSON_MIME_TYPE)
                .body(mapper.writeValueAsString(expected).toResponseBody(JSON_MIME_TYPE.toMediaType())).build()
        })).create(StudyApi::class.java)
        val receipt = api.uploadAndroidSensorDataReceiptV4(study, "participant", "device", samples, 1)
        receipt.validateSubmitted(samples.mapTo(linkedSetOf()) { it.id })
        assertEquals(expected, receipt)
        val operation = specification.substringAfter("  /chronicle/v4/study/{studyId}/participant/{participantId}/android/sensors/receipt:")
            .substringBefore("\n  /chronicle/")
        assertTrue(operation.contains("operationId: uploadAndroidSensorDataReceiptV4"))
        assertTrue(operation.contains("#/components/parameters/collectionVersionHeader"))
        assertTrue(operation.contains("#/components/schemas/SensorUploadReceipt"))
    }

    @Test
    fun `v4 Android uploads send the device header on the v4 wire route`() {
        val studyId = UUID.randomUUID()
        val participantId = "participant"
        val deviceId = "device-instance"
        val usageData = ChronicleData(emptyList())
        val emptyBatch = mapper.writeValueAsString(emptyList<Any>())
        val uploads = listOf(
            Triple("", mapper.writeValueAsString(usageData), { api: StudyApi ->
                api.uploadAndroidUsageEventDataV4(studyId, participantId, deviceId, usageData)
            }),
            Triple("/sensors", emptyBatch, { api: StudyApi ->
                api.uploadAndroidSensorDataV4(studyId, participantId, deviceId, emptyList())
            }),
            Triple("/battery", emptyBatch, { api: StudyApi ->
                api.uploadBatteryTelemetryV4(studyId, participantId, deviceId, emptyList())
            }),
            Triple("/interaction", emptyBatch, { api: StudyApi ->
                api.uploadInteractionEventsV4(studyId, participantId, deviceId, emptyList())
            }),
        )
        uploads.forEach { (suffix, body, upload) ->
            val api = createRetrofitAdapter(createHttpClient({ chain ->
                val request = chain.request()
                assertEquals("POST", request.method)
                assertEquals("/chronicle/v4/study/$studyId/participant/$participantId/android$suffix", request.url.encodedPath)
                assertEquals(deviceId, request.header("X-Chronicle-Device-Id"))
                assertEquals(body, readBody(request.body).toString(Charsets.UTF_8))
                Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                    .header("Content-Type", JSON_MIME_TYPE)
                    .body("0".toResponseBody(JSON_MIME_TYPE.toMediaType())).build()
            })).create(StudyApi::class.java)
            assertEquals(0, upload(api))
        }
    }

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
