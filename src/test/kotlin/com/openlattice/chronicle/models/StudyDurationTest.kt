package com.openlattice.chronicle.models

import com.openlattice.chronicle.study.StudyDuration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StudyDurationTest {

    @Test fun testDefaultConstructor() { val d = StudyDuration(); assertNotNull(d) }
    @Test fun testDefaultYears() { assertEquals(0.toShort(), StudyDuration().years) }
    @Test fun testDefaultMonths() { assertEquals(0.toShort(), StudyDuration().months) }
    @Test fun testDefaultDays() { assertEquals(0.toShort(), StudyDuration().days) }

    @Test fun testCustomYears() { assertEquals(2.toShort(), StudyDuration(years = 2).years) }
    @Test fun testCustomMonths() { assertEquals(6.toShort(), StudyDuration(months = 6).months) }
    @Test fun testCustomDays() { assertEquals(15.toShort(), StudyDuration(days = 15).days) }

    @Test fun testCustomAllFields() {
        val d = StudyDuration(years = 3, months = 11, days = 365)
        assertEquals(3.toShort(), d.years)
        assertEquals(11.toShort(), d.months)
        assertEquals(365.toShort(), d.days)
    }

    @Test fun testEqualityIdentical() { assertEquals(StudyDuration(1, 2, 3), StudyDuration(1, 2, 3)) }
    @Test fun testEqualityDefaults() { assertEquals(StudyDuration(), StudyDuration()) }
    @Test fun testInequalityYears() { assertNotEquals(StudyDuration(1, 2, 3), StudyDuration(2, 2, 3)) }
    @Test fun testInequalityMonths() { assertNotEquals(StudyDuration(1, 2, 3), StudyDuration(1, 3, 3)) }
    @Test fun testInequalityDays() { assertNotEquals(StudyDuration(1, 2, 3), StudyDuration(1, 2, 4)) }
    @Test fun testInequalityNull() { assertNotEquals(StudyDuration(), null) }

    @Test fun testHashCodeConsistency() { assertEquals(StudyDuration(1, 2, 3).hashCode(), StudyDuration(1, 2, 3).hashCode()) }
    @Test fun testHashCodeDefaultConsistency() { assertEquals(StudyDuration().hashCode(), StudyDuration().hashCode()) }
    @Test fun testHashCodeDifference() { assertNotEquals(StudyDuration(1, 2, 3).hashCode(), StudyDuration(4, 5, 6).hashCode()) }

    @Test fun testCopyModifyYears() {
        val d = StudyDuration(1, 2, 3)
        val c = d.copy(years = 5)
        assertEquals(5.toShort(), c.years)
        assertEquals(2.toShort(), c.months)
        assertEquals(3.toShort(), c.days)
    }
    @Test fun testCopyModifyMonths() {
        val d = StudyDuration(1, 2, 3)
        val c = d.copy(months = 9)
        assertEquals(1.toShort(), c.years)
        assertEquals(9.toShort(), c.months)
    }
    @Test fun testCopyModifyDays() {
        val d = StudyDuration(1, 2, 3)
        val c = d.copy(days = 100)
        assertEquals(100.toShort(), c.days)
    }
    @Test fun testCopyUnmodified() {
        val d = StudyDuration(1, 2, 3)
        val c = d.copy()
        assertEquals(d, c)
    }

    @Test fun testToStringNotNull() { assertNotNull(StudyDuration().toString()) }
    @Test fun testToStringContainsClassName() { assertTrue(StudyDuration().toString().contains("StudyDuration")) }
    @Test fun testToStringContainsValues() {
        val s = StudyDuration(years = 5, months = 7, days = 20).toString()
        assertTrue(s.contains("5"))
        assertTrue(s.contains("7"))
        assertTrue(s.contains("20"))
    }

    @Test fun testZeroValues() {
        val d = StudyDuration(0, 0, 0)
        assertEquals(0.toShort(), d.years)
        assertEquals(0.toShort(), d.months)
        assertEquals(0.toShort(), d.days)
    }

    @Test fun testMaxYearsAnnotated() { assertEquals(100.toShort(), StudyDuration(years = 100).years) }
    @Test fun testMaxMonthsAnnotated() { assertEquals(11.toShort(), StudyDuration(months = 11).months) }
    @Test fun testMaxDaysAnnotated() { assertEquals(365.toShort(), StudyDuration(days = 365).days) }

    @Test fun testDestructuring() {
        val (y, m, d) = StudyDuration(3, 6, 90)
        assertEquals(3.toShort(), y)
        assertEquals(6.toShort(), m)
        assertEquals(90.toShort(), d)
    }

    @Test fun testSelfEquality() {
        val d = StudyDuration(1, 2, 3)
        assertEquals(d, d)
    }

    @Test fun testNotEqualsDifferentType() {
        assertNotEquals(StudyDuration(1, 2, 3), "not a duration")
    }
}
