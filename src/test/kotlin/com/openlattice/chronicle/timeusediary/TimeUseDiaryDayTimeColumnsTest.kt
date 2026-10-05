package com.openlattice.chronicle.timeusediary

import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_COUNTER
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_END_TIME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_START_TIME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PARTICIPANT_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_MEDIA_AGE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_MEDIA_NAME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.STUDY_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SUBMISSION_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.TIMESTAMP
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/** The DayTime export fills any column it neither computes nor maps with the literal "UNMAPPED". */
class TimeUseDiaryDayTimeColumnsTest {
    @Test
    fun everyDayTimeColumnIsComputedOrMappedToAQuestion() {
        assertNotEquals(SECONDARY_MEDIA_AGE, SECONDARY_MEDIA_NAME)

        val computed = setOf(SUBMISSION_ID, STUDY_ID, PARTICIPANT_ID, TIMESTAMP,
            ACTIVITY_COUNTER, ACTIVITY_START_TIME, ACTIVITY_END_TIME, ACTIVITY_DURATION)

        val unmapped = TimeUseDiaryDownloadDataType.DayTime.downloadColumnTitles - computed -
            TimeUseDiaryColumnTitles.columnTitleToQuestionCodeMap.keys

        assertEquals(emptySet<String>(), unmapped)
    }
}
