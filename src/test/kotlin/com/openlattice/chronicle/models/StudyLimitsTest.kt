package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.StudyDuration
import com.openlattice.chronicle.study.StudyFeature
import com.openlattice.chronicle.study.StudyLimits
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.EnumSet

class StudyLimitsTest {

    @Test fun testDefaultConstructor() { assertNotNull(StudyLimits()) }

    @Test fun testDefaultStudyDuration() {
        val limits = StudyLimits()
        assertEquals(1.toShort(), limits.studyDuration.years)
        assertEquals(0.toShort(), limits.studyDuration.months)
        assertEquals(0.toShort(), limits.studyDuration.days)
    }

    @Test fun testDefaultDataRetentionDuration() {
        val limits = StudyLimits()
        assertEquals(0.toShort(), limits.dataRetentionDuration.years)
        assertEquals(0.toShort(), limits.dataRetentionDuration.months)
        assertEquals(90.toShort(), limits.dataRetentionDuration.days)
    }

    @Test fun testDefaultParticipantLimit() { assertEquals(25, StudyLimits().participantLimit) }

    @Test fun testDefaultFeaturesContainsChronicle() { assertTrue(StudyLimits().features.contains(StudyFeature.CHRONICLE)) }
    @Test fun testDefaultFeaturesContainsDataCollection() { assertTrue(StudyLimits().features.contains(StudyFeature.CHRONICLE_DATA_COLLECTION)) }
    @Test fun testDefaultFeaturesContainsSurveys() { assertTrue(StudyLimits().features.contains(StudyFeature.CHRONICLE_SURVEYS)) }
    @Test fun testDefaultFeaturesSize() { assertEquals(3, StudyLimits().features.size) }

    @Test fun testStudyEndsNotNull() { assertNotNull(StudyLimits().studyEnds) }
    @Test fun testStudyDataExpiresNotNull() { assertNotNull(StudyLimits().studyDataExpires) }
    @Test fun testStudyDataExpiresAfterStudyEnds() { assertTrue(StudyLimits().studyDataExpires.isAfter(StudyLimits().studyEnds)) }

    @Test fun testCustomStudyDuration() {
        val limits = StudyLimits(studyDuration = StudyDuration(years = 3))
        assertEquals(3.toShort(), limits.studyDuration.years)
    }

    @Test fun testCustomDataRetention() {
        val limits = StudyLimits(dataRetentionDuration = StudyDuration(years = 1))
        assertEquals(1.toShort(), limits.dataRetentionDuration.years)
    }

    @Test fun testCustomParticipantLimit() {
        val limits = StudyLimits(participantLimit = 500)
        assertEquals(500, limits.participantLimit)
    }

    @Test fun testCustomFeatures() {
        val features = EnumSet.of(StudyFeature.APP_USAGE, StudyFeature.TIME_USE_DIARY)
        val limits = StudyLimits(features = features)
        assertEquals(2, limits.features.size)
        assertTrue(limits.features.contains(StudyFeature.APP_USAGE))
        assertTrue(limits.features.contains(StudyFeature.TIME_USE_DIARY))
    }

    @Test fun testEqualStudyLimits() {
        val sd = StudyDuration(years = 1)
        val dr = StudyDuration(days = 90)
        val l1 = StudyLimits(studyDuration = sd, dataRetentionDuration = dr, participantLimit = 25)
        val l2 = StudyLimits(studyDuration = sd, dataRetentionDuration = dr, participantLimit = 25)
        // studyEnds and studyDataExpires are computed at construction time, so these won't be strictly equal
        // unless we pass them explicitly, but the main fields should match
        assertEquals(l1.studyDuration, l2.studyDuration)
        assertEquals(l1.dataRetentionDuration, l2.dataRetentionDuration)
        assertEquals(l1.participantLimit, l2.participantLimit)
    }

    @Test fun testDifferentParticipantLimits() {
        val l1 = StudyLimits(participantLimit = 25)
        val l2 = StudyLimits(participantLimit = 100)
        assertNotEquals(l1.participantLimit, l2.participantLimit)
    }

    @Test fun testToStringNotNull() { assertNotNull(StudyLimits().toString()) }
    @Test fun testToStringContainsClass() { assertTrue(StudyLimits().toString().contains("StudyLimits")) }

    @Test fun testMinParticipantLimit() {
        val limits = StudyLimits(participantLimit = 1)
        assertEquals(1, limits.participantLimit)
    }

    @Test fun testMaxParticipantLimit() {
        val limits = StudyLimits(participantLimit = 100000)
        assertEquals(100000, limits.participantLimit)
    }

    @Test fun testFeaturesEmptySet() {
        val limits = StudyLimits(features = EnumSet.noneOf(StudyFeature::class.java))
        assertTrue(limits.features.isEmpty())
    }

    @Test fun testFeaturesAllValues() {
        val limits = StudyLimits(features = EnumSet.allOf(StudyFeature::class.java))
        assertEquals(StudyFeature.values().size, limits.features.size)
    }
}
