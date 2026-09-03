package com.openlattice.chronicle.models

import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.candidates.Candidate
import com.openlattice.chronicle.ids.IdConstants
import com.openlattice.chronicle.participants.ParticipantStats
import com.openlattice.chronicle.sensorkit.SensorSetting
import com.openlattice.chronicle.sensorkit.SensorType
import com.openlattice.chronicle.study.ComplianceViolation
import com.openlattice.chronicle.study.StudySettingType
import com.openlattice.chronicle.study.StudySettings
import com.openlattice.chronicle.study.StudyUpdate
import com.openlattice.chronicle.study.ViolationReason
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.UUID

class MiscModelsTest {

    // ===== OK =====
    @Test fun testOKDefaultMsg() { assertEquals("SUCCESS", OK().msg) }
    @Test fun testOKCustomMsg() { assertEquals("DONE", OK("DONE").msg) }
    @Test fun testOKCompanion() { assertEquals("SUCCESS", OK.ok.msg) }
    @Test fun testOKEquality() { assertEquals(OK(), OK()) }
    @Test fun testOKInequality() { assertNotEquals(OK("A"), OK("B")) }
    @Test fun testOKHashCode() { assertEquals(OK().hashCode(), OK().hashCode()) }
    @Test fun testOKToString() { assertNotNull(OK().toString()) }

    // ===== Candidate =====
    @Test fun testCandidateDefaultId() { assertEquals(IdConstants.UNINITIALIZED.id, Candidate().id) }
    @Test fun testCandidateCustomId() {
        val id = UUID.randomUUID()
        val c = Candidate(id = id)
        assertEquals(id, c.id)
    }
    @Test fun testCandidateEquality() { assertEquals(Candidate(), Candidate()) }
    @Test fun testCandidateInequalityById() {
        assertNotEquals(Candidate(UUID.randomUUID()), Candidate(UUID.randomUUID()))
    }
    @Test fun testCandidateToString() { assertNotNull(Candidate().toString()) }

    // ===== ParticipantStats =====
    @Test fun testParticipantStatsConstruction() {
        val studyId = UUID.randomUUID()
        val stats = ParticipantStats(studyId = studyId, participantId = "p1")
        assertEquals(studyId, stats.studyId)
        assertEquals("p1", stats.participantId)
    }
    @Test fun testParticipantStatsDefaultAndroidLastPing() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.androidLastPing)
    }
    @Test fun testParticipantStatsDefaultAndroidFirstDate() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.androidFirstDate)
    }
    @Test fun testParticipantStatsDefaultAndroidLastDate() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.androidLastDate)
    }
    @Test fun testParticipantStatsDefaultAndroidUniqueDates() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertTrue(stats.androidUniqueDates.isEmpty())
    }
    @Test fun testParticipantStatsDefaultIosLastPing() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.iosLastPing)
    }
    @Test fun testParticipantStatsDefaultIosFirstDate() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.iosFirstDate)
    }
    @Test fun testParticipantStatsDefaultIosLastDate() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.iosLastDate)
    }
    @Test fun testParticipantStatsDefaultIosUniqueDates() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertTrue(stats.iosUniqueDates.isEmpty())
    }
    @Test fun testParticipantStatsDefaultTudFirstDate() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.tudFirstDate)
    }
    @Test fun testParticipantStatsDefaultTudLastDate() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertNull(stats.tudLastDate)
    }
    @Test fun testParticipantStatsDefaultTudUniqueDates() {
        val stats = ParticipantStats(UUID.randomUUID(), "p1")
        assertTrue(stats.tudUniqueDates.isEmpty())
    }
    @Test fun testParticipantStatsToString() {
        assertNotNull(ParticipantStats(UUID.randomUUID(), "p1").toString())
    }

    // ===== SensorSetting =====
    @Test fun testSensorSettingEmpty() {
        val ss = SensorSetting(emptySet())
        assertTrue(ss.isEmpty())
    }
    @Test fun testSensorSettingWithTypes() {
        val ss = SensorSetting(setOf(SensorType.deviceUsage, SensorType.phoneUsage))
        assertEquals(2, ss.size)
        assertTrue(ss.contains(SensorType.deviceUsage))
    }
    @Test fun testSensorSettingNoSensorsCompanion() {
        assertTrue(SensorSetting.NO_SENSORS.isEmpty())
    }
    @Test fun testSensorSettingHashCode() {
        assertEquals(SensorSetting(emptySet()).hashCode(), SensorSetting(emptySet()).hashCode())
    }

    // ===== ComplianceViolation =====
    @Test fun testComplianceViolationConstruction() {
        val cv = ComplianceViolation(ViolationReason.NO_DATA_UPLOADED, "No data in 24 hours")
        assertEquals(ViolationReason.NO_DATA_UPLOADED, cv.reason)
        assertEquals("No data in 24 hours", cv.description)
    }
    @Test fun testComplianceViolationEquality() {
        assertEquals(
            ComplianceViolation(ViolationReason.NOT_ENROLLED, "desc"),
            ComplianceViolation(ViolationReason.NOT_ENROLLED, "desc")
        )
    }
    @Test fun testComplianceViolationInequality() {
        assertNotEquals(
            ComplianceViolation(ViolationReason.NOT_ENROLLED, "a"),
            ComplianceViolation(ViolationReason.NO_DATA_UPLOADED, "a")
        )
    }
    @Test fun testComplianceViolationToString() {
        assertNotNull(ComplianceViolation(ViolationReason.NOT_ENROLLED, "desc").toString())
    }

    // ===== StudyUpdate =====
    @Test fun testStudyUpdateAllNulls() {
        val update = StudyUpdate()
        assertNull(update.title)
        assertNull(update.description)
        assertNull(update.lat)
        assertNull(update.lon)
        assertNull(update.group)
        assertNull(update.version)
        assertNull(update.settings)
        assertNull(update.modules)
        assertNull(update.contact)
        assertNull(update.notificationsEnabled)
        assertNull(update.storage)
    }
    @Test fun testStudyUpdateCustomTitle() {
        val update = StudyUpdate(title = "New Title")
        assertEquals("New Title", update.title)
    }
    @Test fun testStudyUpdateCustomDescription() {
        val update = StudyUpdate(description = "New Desc")
        assertEquals("New Desc", update.description)
    }
    @Test fun testStudyUpdateCustomLat() {
        val update = StudyUpdate(lat = 29.71)
        assertEquals(29.71, update.lat!!, 0.001)
    }
    @Test fun testStudyUpdateCustomLon() {
        val update = StudyUpdate(lon = -95.39)
        assertEquals(-95.39, update.lon!!, 0.001)
    }
    @Test fun testStudyUpdateCustomContact() {
        val update = StudyUpdate(contact = "researcher@example.com")
        assertEquals("researcher@example.com", update.contact)
    }
    @Test fun testStudyUpdateBlankTitleThrows() {
        try {
            StudyUpdate(title = "   ")
            fail("Expected IllegalArgumentException for blank title")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testStudyUpdateBlankContactThrows() {
        try {
            StudyUpdate(contact = "   ")
            fail("Expected IllegalArgumentException for blank contact")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
    @Test fun testStudyUpdateEquality() {
        assertEquals(StudyUpdate(), StudyUpdate())
    }
    @Test fun testStudyUpdateToString() { assertNotNull(StudyUpdate().toString()) }

    // ===== StudySettings =====
    @Test fun testStudySettingsEmpty() {
        val ss = StudySettings()
        assertTrue(ss.isEmpty())
    }
    @Test fun testStudySettingsWithEntries() {
        val ss = StudySettings(mapOf(StudySettingType.Pipeline to com.openlattice.chronicle.pipeline.PipelineConfig()))
        assertEquals(1, ss.size)
        assertTrue(ss.containsKey(StudySettingType.Pipeline))
    }
    @Test fun testStudySettingsEquality() {
        assertEquals(StudySettings(), StudySettings())
    }
    @Test fun testStudySettingsHashCode() {
        assertEquals(StudySettings().hashCode(), StudySettings().hashCode())
    }
}
