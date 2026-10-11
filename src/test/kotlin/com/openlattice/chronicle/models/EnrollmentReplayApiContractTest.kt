package com.openlattice.chronicle.models

import com.openlattice.chronicle.ChronicleTestUtils.JSON_MIME_TYPE
import com.openlattice.chronicle.ChronicleTestUtils.createHttpClient
import com.openlattice.chronicle.ChronicleTestUtils.createRetrofitAdapter
import com.openlattice.chronicle.ChronicleTestUtils.readBody
import com.openlattice.chronicle.sources.AndroidDevice
import com.openlattice.chronicle.study.EnrollmentResponse
import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.util.RetrofitBuilders.mapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.util.UUID

class EnrollmentReplayApiContractTest {
    private val specification: String by lazy { File("chronicle.yaml").readText() }

    @Test
    fun enrollV4CarriesTheDurableAttemptAndClientProposedCredential() {
        val studyId = UUID.randomUUID()
        val participantId = "participant"
        val device = AndroidDevice("phone", "model", "codename", "brand", "14", "34", "product", "device-instance")
        val enrollmentCode = "invitation-code"
        val manifestDigest = "manifest-digest"
        val attemptId = UUID.randomUUID().toString()
        val proposedApiKey = "ck_testprefix_testcredential"
        val expected = EnrollmentResponse(UUID.randomUUID(), apiKey = proposedApiKey)
        val headers = mapOf(
            "X-Chronicle-Device-Id" to device.deviceId,
            "X-Chronicle-Enrollment-Code" to enrollmentCode,
            "X-Chronicle-Manifest-Digest" to manifestDigest,
            "X-Chronicle-Enrollment-Attempt-Id" to attemptId,
            "X-Chronicle-Proposed-Api-Key" to proposedApiKey,
        )
        val api = createRetrofitAdapter(createHttpClient({ chain ->
            val request = chain.request()
            assertEquals("POST", request.method)
            assertEquals("/chronicle/v4/study/$studyId/participant/$participantId/enroll", request.url.encodedPath)
            headers.forEach { (name, value) -> assertEquals(name, value, request.header(name)) }
            assertEquals(device, mapper.readValue(readBody(request.body), AndroidDevice::class.java))
            Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(200).message("OK")
                .header("Content-Type", JSON_MIME_TYPE)
                .body(mapper.writeValueAsString(expected).toResponseBody(JSON_MIME_TYPE.toMediaType())).build()
        })).create(StudyApi::class.java)

        assertEquals(expected, api.enrollV4(
            studyId, participantId, device.deviceId, device,
            enrollmentCode, manifestDigest, attemptId, proposedApiKey,
        ))
    }

    @Test
    fun enrollV4OpenApiPublishesTheDurableAttemptAndClientProposedCredential() {
        val enrollmentPath = specification
            .substringAfter("/chronicle/v4/study/{studyId}/participant/{participantId}/enroll:")
            .substringBefore("/chronicle/v4/mobile/enrollments/current:")

        assertTrue(enrollmentPath.contains("name: X-Chronicle-Enrollment-Attempt-Id"))
        assertTrue(enrollmentPath.contains("name: X-Chronicle-Proposed-Api-Key"))
        assertTrue(enrollmentPath.contains("all four invitation credential headers"))
        assertTrue(enrollmentPath.contains("stable across exact retries"))
    }
}
