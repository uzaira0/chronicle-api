package com.openlattice.chronicle.timeusediary

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public class TimeUseDiaryColumnTitles private constructor() {
    public companion object {
        public const val ACTIVITY_COUNTER: String = "Counter"
        public const val ACTIVITY_DURATION: String = "Duration(Min)"
        public const val ACTIVITY_END_TIME: String = "Activity_End"
        public const val ACTIVITY_START_TIME: String = "Activity_Start"
        public const val ADULT_MEDIA: String = "Adult_Media_Use"
        public const val BED_TIME_YESTERDAY: String = "Bedtime_Yesterday"
        public const val BG_AUDIO_DAY: String = "Background_Audio_Day"
        public const val BG_AUDIO_NIGHT: String = "Background_Audio_Night"
        public const val BG_TV_DAY: String = "Background_TV_Day"
        public const val BG_TV_NIGHT: String = "Background_TV_Night"
        public const val CAREGIVER: String = "Caregiver"
        public const val COLLABORATOR: String = "collaborator"
        public const val DAY_OF_WEEK: String = "Day"
        public const val DAY_TIME_HOURS: String = "Daytime_Hours"
        public const val FAMILY_ID: String = "Family_ID"
        public const val NON_TYPICAL_DAY_REASON: String = "Non_Typical_Reason"
        public const val NON_TYPICAL_SLEEP_PATTERN: String = "Non_Typical_Sleep_Pattern"
        public const val PARTICIPANT_ID: String = "Participant_ID"
        public const val PRIMARY_ACTIVITY: String = "Primary_Activity"
        public const val PRIMARY_BOOK_TITLE: String = "Primary_Book_Title"
        public const val PRIMARY_BOOK_TYPE: String = "Primary_Book_Type"
        public const val PRIMARY_DEVICE_TYPE: String = "Primary_Device_Type"
        public const val PRIMARY_MEDIA_ACTIVITY: String ="Primary_Media_Activity"
        public const val PRIMARY_MEDIA_AGE: String = "Primary_Media_Age"
        public const val PRIMARY_MEDIA_NAME: String = "Primary_Media_Name"
        public const val SECONDARY_ACTIVITY: String = "Secondary_Activity"
        public const val SECONDARY_BOOK_TITLE: String ="Secondary_Book_Title"
        public const val SECONDARY_BOOK_TYPE: String = "Secondary_Book_Type"
        public const val SECONDARY_MEDIA_ACTIVITY: String = "Secondary_Media_Activity"
        public const val SECONDARY_MEDIA_AGE: String = "Secondary_Media_Age"
        public const val SECONDARY_MEDIA_NAME: String = "Secondary_Media_Age"
        public const val SLEEP_ARRANGEMENT: String = "Sleeping_Arrangement"
        public const val SLEEP_HOURS: String = "Sleep_Hours"
        public const val STUDY_ID: String = "Study_ID"
        public const val SUBMISSION_ID: String = "Submission_ID"
        public const val TIMESTAMP: String = "Timestamp"
        public const val TYPICAL_DAY: String = "Typical_Day"
        public const val SLEEP_PATTERN: String = "Typical_Sleep_Pattern"
        public const val WAKE_UP_COUNT: String = "Wake_Up_Count"
        public const val WAKE_UP_TODAY: String = "Wakeup_Today"
        public const val WAKE_UP_YESTERDAY: String = "Wakeup_Yesterday"
        public const val WAVE_ID: String = "Wave_ID"
        public const val ACTIVITY_DAY: String = "Activity_Day"
        public const val ACTIVITY_DATE: String = "Activity_Date"
        public const val WAKE_UP_TIME_AFTER_ACTIVITY_DAY: String = "Wake_Up_Time_After_Activity_Day"
        public const val BEDTIME_AFTER_ACTIVITY_DAY: String = "Bedtime_After_Activity_Day"

        public val columnTitleToQuestionCodeMap: Map<String, String> = mapOf(
            FAMILY_ID to TimeUseDiaryQuestionCodes.FAMILY_ID,
            WAVE_ID to TimeUseDiaryQuestionCodes.WAVE_ID,
            DAY_OF_WEEK to TimeUseDiaryQuestionCodes.DAY_OF_WEEK,
            TYPICAL_DAY to TimeUseDiaryQuestionCodes.TYPICAL_DAY,
            NON_TYPICAL_DAY_REASON to TimeUseDiaryQuestionCodes.NON_TYPICAL_DAY_REASON,
            SLEEP_PATTERN to TimeUseDiaryQuestionCodes.SLEEP_PATTERN,
            SLEEP_ARRANGEMENT to TimeUseDiaryQuestionCodes.SLEEP_ARRANGEMENT,
            NON_TYPICAL_SLEEP_PATTERN to TimeUseDiaryQuestionCodes.NON_TYPICAL_SLEEP_PATTERN,
            WAKE_UP_COUNT to TimeUseDiaryQuestionCodes.WAKE_UP_COUNT,
            BG_TV_NIGHT to TimeUseDiaryQuestionCodes.BG_TV_NIGHT,
            BG_AUDIO_NIGHT to TimeUseDiaryQuestionCodes.BG_AUDIO_NIGHT,
            CAREGIVER to TimeUseDiaryQuestionCodes.CAREGIVER,
            COLLABORATOR to TimeUseDiaryQuestionCodes.COLLABORATOR,
            PRIMARY_MEDIA_ACTIVITY to TimeUseDiaryQuestionCodes.PRIMARY_MEDIA_ACTIVITY,
            PRIMARY_ACTIVITY to TimeUseDiaryQuestionCodes.PRIMARY_ACTIVITY,
            PRIMARY_MEDIA_AGE to TimeUseDiaryQuestionCodes.PRIMARY_MEDIA_AGE,
            PRIMARY_MEDIA_NAME to TimeUseDiaryQuestionCodes.PRIMARY_MEDIA_NAME,
            PRIMARY_BOOK_TYPE to TimeUseDiaryQuestionCodes.PRIMARY_BOOK_TYPE,
            PRIMARY_BOOK_TITLE to TimeUseDiaryQuestionCodes.PRIMARY_BOOK_TITLE,
            PRIMARY_DEVICE_TYPE to TimeUseDiaryQuestionCodes.PRIMARY_DEVICE_TYPE,
            SECONDARY_MEDIA_ACTIVITY to TimeUseDiaryQuestionCodes.SECONDARY_MEDIA_ACTIVITY,
            SECONDARY_MEDIA_AGE to TimeUseDiaryQuestionCodes.SECONDARY_MEDIA_AGE,
            SECONDARY_MEDIA_NAME to TimeUseDiaryQuestionCodes.SECONDARY_MEDIA_NAME,
            SECONDARY_BOOK_TYPE to TimeUseDiaryQuestionCodes.SECONDARY_BOOK_TYPE,
            SECONDARY_BOOK_TITLE to TimeUseDiaryQuestionCodes.SECONDARY_BOOK_TITLE,
            SECONDARY_ACTIVITY to TimeUseDiaryQuestionCodes.SECONDARY_ACTIVITY,
            BG_TV_DAY to TimeUseDiaryQuestionCodes.BG_TV_DAY,
            BG_AUDIO_DAY to TimeUseDiaryQuestionCodes.BG_AUDIO_DAY,
            ADULT_MEDIA to TimeUseDiaryQuestionCodes.ADULT_MEDIA,
            ACTIVITY_DAY to TimeUseDiaryQuestionCodes.ACTIVITY_DAY,
            ACTIVITY_DATE to TimeUseDiaryQuestionCodes.ACTIVITY_DATE,
            WAKE_UP_TIME_AFTER_ACTIVITY_DAY to TimeUseDiaryQuestionCodes.WAKE_UP_TIME_AFTER_ACTIVITY_DAY,
            BEDTIME_AFTER_ACTIVITY_DAY to TimeUseDiaryQuestionCodes.BED_TIME_BEFORE_ACTIVITY_DAY,
            WAKE_UP_TODAY to TimeUseDiaryQuestionCodes.TODAY_WAKEUP_TIME,
        )
    }
}
