package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.StudyDuration
import com.openlattice.chronicle.study.StudyLimits
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StudyLimitsTest {

    @Test fun testDefaultConstructor() { assertNotNull(StudyLimits()) }

    @Test fun aDefaultStudyHasNoEnd() {
        val limits = StudyLimits()
        assertNull(limits.studyDuration)
        assertNull(limits.studyEnds)
        assertNull(limits.studyDataExpires)
    }

    @Test fun testDefaultDataRetentionDuration() {
        val limits = StudyLimits()
        assertEquals(0.toShort(), limits.dataRetentionDuration.years)
        assertEquals(0.toShort(), limits.dataRetentionDuration.months)
        assertEquals(90, limits.dataRetentionDuration.days)
    }

    @Test fun aSetDurationDerivesTheEndAndDataExpiry() {
        val limits = StudyLimits(studyDuration = StudyDuration(years = 1))
        assertTrue(limits.studyDataExpires!!.isAfter(limits.studyEnds))
    }

    @Test fun testCustomStudyDuration() {
        val limits = StudyLimits(studyDuration = StudyDuration(years = 3))
        assertEquals(3.toShort(), limits.studyDuration?.years)
    }

    @Test fun testCustomDataRetention() {
        val limits = StudyLimits(dataRetentionDuration = StudyDuration(years = 1))
        assertEquals(1.toShort(), limits.dataRetentionDuration.years)
    }

    @Test fun testEqualStudyLimits() {
        val sd = StudyDuration(years = 1)
        val dr = StudyDuration(days = 90)
        val l1 = StudyLimits(studyDuration = sd, dataRetentionDuration = dr)
        val l2 = StudyLimits(studyDuration = sd, dataRetentionDuration = dr)
        // studyEnds and studyDataExpires are computed at construction time, so these won't be strictly equal
        // unless we pass them explicitly, but the main fields should match
        assertEquals(l1.studyDuration, l2.studyDuration)
        assertEquals(l1.dataRetentionDuration, l2.dataRetentionDuration)
    }

    @Test fun testToStringNotNull() { assertNotNull(StudyLimits().toString()) }
    @Test fun testToStringContainsClass() { assertTrue(StudyLimits().toString().contains("StudyLimits")) }
}
