package com.openlattice.chronicle.models

import com.openlattice.chronicle.collection.CollectionAcknowledgment
import com.openlattice.chronicle.collection.CollectionModuleId
import jakarta.validation.Validation
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime

class CollectionAcknowledgmentLengthContractTest {
    private val validator = Validation.byDefaultProvider().configure()
        .messageInterpolator(ParameterMessageInterpolator()).buildValidatorFactory().validator

    private fun acknowledgment(version: String) = CollectionAcknowledgment(
        acknowledgedModules = setOf(CollectionModuleId.BATTERY_TELEMETRY),
        acknowledgedAt = OffsetDateTime.parse("2026-06-04T12:00:00Z"), appVersion = version,
    )

    @Test fun thePublicValidatorAndConstructorUseTheSameExistingAppVersionBudget() {
        val versions = linkedMapOf("ascii64" to "a".repeat(64), "ascii65" to "a".repeat(65),
            "astral32" to "\uD83D\uDE00".repeat(32), "astral33" to "\uD83D\uDE00".repeat(33))
        val mismatches = versions.mapNotNull { (name, value) ->
            val constructed = runCatching { acknowledgment(value) }.isSuccess
            val validated = validator.validateValue(CollectionAcknowledgment::class.java, "appVersion", value).isEmpty()
            if (constructed == validated) null else "$name: constructor=$constructed, bean=$validated"
        }
        assertEquals(emptyList<String>(), mismatches)
    }

    @Test fun theExistingConstructorBudgetPreservesSupplementaryCharactersWithinSixtyFourUtf16Units() {
        assertTrue(runCatching { acknowledgment("a".repeat(64)) }.isSuccess)
        assertTrue(runCatching { acknowledgment("\uD83D\uDE00".repeat(32)) }.isSuccess)
        assertFalse(runCatching { acknowledgment("a".repeat(65)) }.isSuccess)
        assertFalse(runCatching { acknowledgment("\uD83D\uDE00".repeat(33)) }.isSuccess)
    }
}
