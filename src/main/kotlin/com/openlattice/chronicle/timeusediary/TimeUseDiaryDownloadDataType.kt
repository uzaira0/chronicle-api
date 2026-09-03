package com.openlattice.chronicle.timeusediary

import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_COUNTER
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_DATE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_DAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_END_TIME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ACTIVITY_START_TIME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.ADULT_MEDIA
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.BEDTIME_AFTER_ACTIVITY_DAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.BED_TIME_YESTERDAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.BG_AUDIO_DAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.BG_AUDIO_NIGHT
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.BG_TV_DAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.BG_TV_NIGHT
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.CAREGIVER
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.COLLABORATOR
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.DAY_OF_WEEK
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.DAY_TIME_HOURS
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.FAMILY_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.NON_TYPICAL_DAY_REASON
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.NON_TYPICAL_SLEEP_PATTERN
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PARTICIPANT_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_ACTIVITY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_BOOK_TITLE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_BOOK_TYPE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_DEVICE_TYPE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_MEDIA_ACTIVITY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_MEDIA_AGE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.PRIMARY_MEDIA_NAME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_ACTIVITY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_BOOK_TITLE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_BOOK_TYPE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_MEDIA_ACTIVITY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_MEDIA_AGE
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SECONDARY_MEDIA_NAME
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SLEEP_ARRANGEMENT
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SLEEP_HOURS
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SLEEP_PATTERN
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.STUDY_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.SUBMISSION_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.TIMESTAMP
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.TYPICAL_DAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.WAKE_UP_COUNT
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.WAKE_UP_TIME_AFTER_ACTIVITY_DAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.WAKE_UP_TODAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.WAKE_UP_YESTERDAY
import com.openlattice.chronicle.timeusediary.TimeUseDiaryColumnTitles.Companion.WAVE_ID
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.ADULT_MEDIA_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.ADULT_MEDIA_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.BD_MEDIA_DAY_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.BG_AUDIO_DAY_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.BG_AUDIO_DAY_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.BG_MEDIA_DAY_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.BG_TV_DAY_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.BG_TV_DAY_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.DAY_START_TIME
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_BATHROOM_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_BATHROOM_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_CHILDCARE_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_CHILDCARE_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_EATING_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_MEDIA_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_MEDIA_CO_VIEWERS_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_MEDIA_CO_VIEWERS_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_MEDIA_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_MEDIA_PLAY_OUTSIDE_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_OTHER_AT_HOME_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_OTHER_AT_HOME_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_PLAY_INSIDE_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_PLAY_INSIDE_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_PLAY_OUTSIDE_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_READING_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.PRIMARY_READING_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.SCREEN_DURATION_PLAYING_PROPORTION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.SCREEN_DURING_EATING_PROPORTION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.SCREEN_DURING_SLEEPING_DURATION
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.SCREEN_ONE_HOUR_FROM_SLEEPING_BLOCKS
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.TODAY_WAKE_UP_TIME
import com.openlattice.chronicle.timeusediary.TimeUseDiarySummarizedDataVariables.Companion.TYPICAL_SLEEP_PATTERN

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public enum class TimeUseDiaryDownloadDataType {
    DayTime {
        override val downloadColumnTitles: LinkedHashSet<String>
            get() = linkedSetOf(
                SUBMISSION_ID,
                STUDY_ID,
                PARTICIPANT_ID,
                FAMILY_ID,
                WAVE_ID,
                TIMESTAMP,
                ACTIVITY_DATE,
                DAY_OF_WEEK,
                ACTIVITY_DAY,
                TYPICAL_DAY,
                NON_TYPICAL_DAY_REASON,
                ACTIVITY_COUNTER,
                PRIMARY_ACTIVITY,
                ACTIVITY_START_TIME,
                ACTIVITY_END_TIME,
                ACTIVITY_DURATION,
                CAREGIVER,
                COLLABORATOR,
                PRIMARY_MEDIA_ACTIVITY,
                PRIMARY_MEDIA_AGE,
                PRIMARY_MEDIA_NAME,
                PRIMARY_BOOK_TYPE,
                PRIMARY_BOOK_TITLE,
                PRIMARY_DEVICE_TYPE,
                SECONDARY_MEDIA_ACTIVITY,
                SECONDARY_MEDIA_AGE,
                SECONDARY_MEDIA_NAME,
                SECONDARY_BOOK_TYPE,
                SECONDARY_BOOK_TITLE,
                SECONDARY_ACTIVITY,
                BG_TV_DAY,
                BG_AUDIO_DAY,
                ADULT_MEDIA,
                WAKE_UP_YESTERDAY,
                WAKE_UP_TIME_AFTER_ACTIVITY_DAY,
                BEDTIME_AFTER_ACTIVITY_DAY,
            )
    },
    NightTime {
        override val downloadColumnTitles: LinkedHashSet<String>
            get() = linkedSetOf(
                SUBMISSION_ID,
                STUDY_ID,
                PARTICIPANT_ID,
                FAMILY_ID,
                WAVE_ID,
                TIMESTAMP,
                ACTIVITY_DATE,
                DAY_OF_WEEK,
                ACTIVITY_DAY,
                TYPICAL_DAY,
                NON_TYPICAL_DAY_REASON,
                WAKE_UP_YESTERDAY,
                WAKE_UP_TIME_AFTER_ACTIVITY_DAY,
                BEDTIME_AFTER_ACTIVITY_DAY,
                BED_TIME_YESTERDAY,
                WAKE_UP_TODAY,
                DAY_TIME_HOURS,
                SLEEP_HOURS,
                SLEEP_PATTERN,
                NON_TYPICAL_SLEEP_PATTERN,
                SLEEP_ARRANGEMENT,
                WAKE_UP_COUNT,
                BG_TV_NIGHT,
                BG_AUDIO_NIGHT,
                PRIMARY_DEVICE_TYPE,
                COLLABORATOR,
            )
    },
    Summarized {
        override val downloadColumnTitles: LinkedHashSet<String>
            get() = linkedSetOf(
                SUBMISSION_ID,
                STUDY_ID,
                PARTICIPANT_ID,
                TIMESTAMP,
                TimeUseDiarySummarizedDataVariables.FAMILY_ID,
                TimeUseDiarySummarizedDataVariables.WAVE_ID,
                PRIMARY_BATHROOM_DURATION,
                ADULT_MEDIA_BLOCKS,
                TimeUseDiarySummarizedDataVariables.SLEEP_ARRANGEMENT,
                TimeUseDiarySummarizedDataVariables.BG_AUDIO_NIGHT,
                PRIMARY_CHILDCARE_BLOCKS,
                PRIMARY_MEDIA_DURATION,
                TimeUseDiarySummarizedDataVariables.DAY_TIME_END,
                PRIMARY_MEDIA_CO_VIEWERS_BLOCKS,
                BD_MEDIA_DAY_BLOCKS,
                PRIMARY_CHILDCARE_DURATION,
                PRIMARY_PLAY_INSIDE_DURATION,
                ADULT_MEDIA_DURATION,
                TimeUseDiarySummarizedDataVariables.DAY_OF_WEEK,
                TYPICAL_SLEEP_PATTERN,
                TimeUseDiarySummarizedDataVariables.WAKE_UP_COUNT,
                BG_MEDIA_DAY_DURATION,
                SCREEN_ONE_HOUR_FROM_SLEEPING_BLOCKS,
                TimeUseDiarySummarizedDataVariables.TYPICAL_DAY,
                TimeUseDiarySummarizedDataVariables.SECONDARY_BOOK_TITLE,
                SCREEN_DURATION_PLAYING_PROPORTION,
                PRIMARY_EATING_DURATION,
                BG_TV_DAY_DURATION,
                PRIMARY_READING_DURATION,
                PRIMARY_PLAY_INSIDE_BLOCKS,
                TODAY_WAKE_UP_TIME,
                PRIMARY_OTHER_AT_HOME_BLOCKS,
                PRIMARY_MEDIA_BLOCKS,
                PRIMARY_BATHROOM_BLOCKS,
                SCREEN_DURING_SLEEPING_DURATION,
                PRIMARY_OTHER_AT_HOME_DURATION,
                TimeUseDiarySummarizedDataVariables.PRIMARY_MEDIA_NAME,
                BG_TV_DAY_BLOCKS,
                PRIMARY_MEDIA_CO_VIEWERS_DURATION,
                PRIMARY_MEDIA_PLAY_OUTSIDE_DURATION,
                DAY_START_TIME,
                SCREEN_DURING_EATING_PROPORTION,
                BG_AUDIO_DAY_DURATION,
                TimeUseDiarySummarizedDataVariables.NON_TYPICAL_DAY_REASON,
                PRIMARY_PLAY_OUTSIDE_BLOCKS,
                TimeUseDiarySummarizedDataVariables.BG_TV_NIGHT,
                PRIMARY_READING_BLOCKS,
                BG_AUDIO_DAY_BLOCKS,
                TimeUseDiarySummarizedDataVariables.PRIMARY_EATING_BLOCKS,
                TimeUseDiarySummarizedDataVariables.PRIMARY_BOOK_TITLE,
                TimeUseDiarySummarizedDataVariables.SECONDARY_MEDIA_NAME,
                TimeUseDiarySummarizedDataVariables.NON_TYPICAL_SLEEP_REASON,
            )
    };

    public abstract val downloadColumnTitles: LinkedHashSet<String>
}
