package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.StudyApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.http.Header
import java.io.File

class EnrollmentReplayApiContractTest {
    private val specification: String by lazy { File("chronicle.yaml").readText() }

    @Test
    fun enrollV4CarriesTheDurableAttemptAndClientProposedCredential() {
        val method = StudyApi::class.java.methods.single {
            it.name == "enrollV4" && it.parameterCount == 8
        }
        val headerNames = method.parameterAnnotations
            .flatMap { annotations -> annotations.filterIsInstance<Header>() }
            .map(Header::value)

        assertEquals(
            listOf(
                "X-Chronicle-Device-Id",
                "X-Chronicle-Enrollment-Code",
                "X-Chronicle-Manifest-Digest",
                "X-Chronicle-Enrollment-Attempt-Id",
                "X-Chronicle-Proposed-Api-Key",
            ),
            headerNames,
        )
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
