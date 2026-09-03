package com.openlattice.chronicle.enums

import com.openlattice.chronicle.data.ParticipationStatus
import com.openlattice.chronicle.timeusediary.TimeUseDiaryDownloadDataType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class ParticipationStatusEnumTest {

    // ===== ParticipationStatus (5 values) =====
    @Test fun testParticipationStatusCount() { assertEquals(5, ParticipationStatus.values().size) }
    @Test fun testParticipationStatusEnrolled() { assertEquals("ENROLLED", ParticipationStatus.ENROLLED.name) }
    @Test fun testParticipationStatusNotEnrolled() { assertEquals("NOT_ENROLLED", ParticipationStatus.NOT_ENROLLED.name) }
    @Test fun testParticipationStatusPaused() { assertEquals("PAUSED", ParticipationStatus.PAUSED.name) }
    @Test fun testParticipationStatusCollectionCompleted() { assertEquals("COLLECTION_COMPLETED", ParticipationStatus.COLLECTION_COMPLETED.name) }
    @Test fun testParticipationStatusUnknown() { assertEquals("UNKNOWN", ParticipationStatus.UNKNOWN.name) }
    @Test fun testParticipationStatusOrdinalEnrolled() { assertEquals(0, ParticipationStatus.ENROLLED.ordinal) }
    @Test fun testParticipationStatusOrdinalNotEnrolled() { assertEquals(1, ParticipationStatus.NOT_ENROLLED.ordinal) }
    @Test fun testParticipationStatusOrdinalPaused() { assertEquals(2, ParticipationStatus.PAUSED.ordinal) }
    @Test fun testParticipationStatusOrdinalCollectionCompleted() { assertEquals(3, ParticipationStatus.COLLECTION_COMPLETED.ordinal) }
    @Test fun testParticipationStatusOrdinalUnknown() { assertEquals(4, ParticipationStatus.UNKNOWN.ordinal) }
    @Test fun testParticipationStatusValueOfEnrolled() { assertEquals(ParticipationStatus.ENROLLED, ParticipationStatus.valueOf("ENROLLED")) }
    @Test fun testParticipationStatusValueOfInvalid() {
        try {
            ParticipationStatus.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== TimeUseDiaryDownloadDataType (3 values) =====
    @Test fun testTimeUseDiaryDownloadDataTypeCount() { assertEquals(3, TimeUseDiaryDownloadDataType.values().size) }
    @Test fun testTimeUseDiaryDownloadDataTypeDayTime() { assertEquals("DayTime", TimeUseDiaryDownloadDataType.DayTime.name) }
    @Test fun testTimeUseDiaryDownloadDataTypeNightTime() { assertEquals("NightTime", TimeUseDiaryDownloadDataType.NightTime.name) }
    @Test fun testTimeUseDiaryDownloadDataTypeSummarized() { assertEquals("Summarized", TimeUseDiaryDownloadDataType.Summarized.name) }
    @Test fun testTimeUseDiaryDownloadDataTypeOrdinalDayTime() { assertEquals(0, TimeUseDiaryDownloadDataType.DayTime.ordinal) }
    @Test fun testTimeUseDiaryDownloadDataTypeOrdinalNightTime() { assertEquals(1, TimeUseDiaryDownloadDataType.NightTime.ordinal) }
    @Test fun testTimeUseDiaryDownloadDataTypeOrdinalSummarized() { assertEquals(2, TimeUseDiaryDownloadDataType.Summarized.ordinal) }
    @Test fun testTimeUseDiaryDayTimeColumnsNotEmpty() { assertTrue(TimeUseDiaryDownloadDataType.DayTime.downloadColumnTitles.isNotEmpty()) }
    @Test fun testTimeUseDiaryNightTimeColumnsNotEmpty() { assertTrue(TimeUseDiaryDownloadDataType.NightTime.downloadColumnTitles.isNotEmpty()) }
    @Test fun testTimeUseDiarySummarizedColumnsNotEmpty() { assertTrue(TimeUseDiaryDownloadDataType.Summarized.downloadColumnTitles.isNotEmpty()) }
    @Test fun testTimeUseDiaryValueOfDayTime() { assertEquals(TimeUseDiaryDownloadDataType.DayTime, TimeUseDiaryDownloadDataType.valueOf("DayTime")) }
    @Test fun testTimeUseDiaryValueOfInvalid() {
        try {
            TimeUseDiaryDownloadDataType.valueOf("INVALID")
            fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
