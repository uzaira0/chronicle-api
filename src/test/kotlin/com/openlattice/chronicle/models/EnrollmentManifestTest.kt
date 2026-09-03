package com.openlattice.chronicle.models

import com.openlattice.chronicle.collection.AndroidDataCollectionSetting
import com.openlattice.chronicle.collection.CollectionDefaults
import com.openlattice.chronicle.study.EnrollmentManifest
import com.openlattice.chronicle.study.EnrollmentPreviewResponse
import com.openlattice.chronicle.study.StudyParticipantPolicy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

class EnrollmentManifestTest {

    private val now = OffsetDateTime.parse("2026-08-17T12:00:00Z")

    private fun policy() = StudyParticipantPolicy(
        responsibleInstitution = "Example Research Institute",
        serverOperator = "Example Research Institute",
        researchContact = "study-team@example.org",
        purpose = "Study daily routines.",
        expectedDuration = "Twelve weeks",
        procedures = "Chronicle collects approved modules.",
        foreseeableRisks = "Daily patterns may be sensitive.",
        expectedBenefits = "No direct benefit is promised.",
        dataUseAndSharing = "Coded data is available to the approved study team.",
        retentionAndDeletion = "Data is retained for seven years.",
        privacyPolicyUrl = "https://research.example.org/privacy",
        withdrawalUrl = "https://research.example.org/withdraw",
        version = "consent-1",
        effectiveAt = now,
    )

    private fun manifest(
        serverOrigin: String = "https://research.example.org",
        issuedAt: OffsetDateTime = now,
        expiresAt: OffsetDateTime = now.plusMinutes(10),
    ) = EnrollmentManifest(
        serverOrigin = serverOrigin,
        studyId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        participantId = "participant-001",
        studyTitle = "Example Study",
        studyDescription = "A study of daily routines.",
        participantPolicy = policy(),
        collectionSettings = AndroidDataCollectionSetting(
            modules = CollectionDefaults.androidDataCollectionSetting().modules,
            settingsVersion = 3,
        ),
        settingsVersion = 3,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )

    @Test
    fun `manifest carries authoritative study policy and exact collection revision`() {
        val manifest = manifest()

        assertEquals("Example Study", manifest.studyTitle)
        assertEquals("consent-1", manifest.participantPolicy.version)
        assertEquals(3, manifest.settingsVersion)
        assertEquals(3, manifest.collectionSettings.settingsVersion)
    }

    @Test
    fun `manifest requires an https root server origin`() {
        listOf(
            "http://research.example.org",
            "https://research.example.org/chronicle",
            "https://research.example.org?tenant=a",
            "https://user:password@research.example.org",
        ).forEach { invalidOrigin ->
            assertThrows(IllegalArgumentException::class.java) { manifest(serverOrigin = invalidOrigin) }
        }
    }

    @Test
    fun `manifest expires after it is issued`() {
        assertThrows(IllegalArgumentException::class.java) {
            manifest(expiresAt = now.minusSeconds(1))
        }
    }

    @Test
    fun `preview requires lowercase sha256 digest`() {
        assertThrows(IllegalArgumentException::class.java) {
            EnrollmentPreviewResponse(manifest(), "not-a-digest")
        }
        assertEquals("a".repeat(64), EnrollmentPreviewResponse(manifest(), "a".repeat(64)).manifestDigest)
    }
}
