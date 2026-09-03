package com.openlattice.chronicle.models

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class WithdrawalApiContractTest {
    private val specification: String by lazy { File("chronicle.yaml").readText() }

    @Test
    fun `withdrawal requires the durable client request id header`() {
        val withdrawalPath = specification.substringAfter("/chronicle/v4/mobile/enrollments/current:")
            .substringBefore("/chronicle/v4/study/")
            .replace(Regex("\\s+"), " ")

        assertTrue(withdrawalPath.contains("name: X-Chronicle-Withdrawal-Request-Id"))
        assertTrue(withdrawalPath.contains("required: true"))
        assertTrue(withdrawalPath.contains("canonical lowercase UUID"))
        assertTrue(withdrawalPath.contains("exact bound retry"))
        assertFalse(withdrawalPath.contains("derived deterministically from the credential"))
    }

    @Test
    fun `withdrawal response describes the client bound request id`() {
        val responseSchema = specification.substringAfter("EnrollmentWithdrawalResponse:")
            .substringBefore("DeviceEnrollmentEvent:")
            .replace(Regex("\\s+"), " ")

        assertTrue(responseSchema.contains("exact client-supplied UUID"))
        assertTrue(responseSchema.contains("Client-generated withdrawal request UUID"))
        assertTrue(responseSchema.contains("another device"))
        assertTrue(responseSchema.contains("Empty when"))
        assertTrue(responseSchema.contains("another request already owns"))
        assertFalse(responseSchema.contains("Deterministic withdrawal request UUID"))
    }
}
