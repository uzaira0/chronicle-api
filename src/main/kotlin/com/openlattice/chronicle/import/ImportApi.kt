package com.openlattice.chronicle.import

import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public interface ImportApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/import"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val STUDIES: String = "/studies"
        public const val PARTICIPANT_STATS: String = "/participant-stats"
        public const val APP_USAGE_SURVEY: String = "/app-usage-survey"
        public const val SYSTEM_APPS: String ="/system-apps"
        public const val TIME_USE_DIARY: String = "/time-use-diary"
        public const val PERMISSIONS: String = "/permissions"
        public const val PARTICIPANTS: String = "/participants"
    }

    @POST(BASE + STUDIES)
    public fun importStudies(@Body config: ImportStudiesConfiguration)

    @POST(BASE + PARTICIPANT_STATS)
    public fun importParticipantStats(@Body config: ImportStudiesConfiguration)

    @POST(BASE + APP_USAGE_SURVEY)
    public fun importAppUsageSurvey(@Body config: ImportStudiesConfiguration)

    @POST(BASE + SYSTEM_APPS)
    public fun importSystemApps(@Body config: ImportStudiesConfiguration)

    @POST(BASE + TIME_USE_DIARY)
    public fun importTimeUseDiarySubmissions(@Body config: ImportStudiesConfiguration)

    @POST(BASE + PERMISSIONS)
    public fun importUserPermissions(@Body config: ImportStudiesConfiguration)

    @POST(BASE + PARTICIPANTS)
    public fun importParticipants(@Body config: ImportStudiesConfiguration)
}
