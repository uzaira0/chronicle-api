package com.openlattice.chronicle.timeusediary

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public interface TimeUseDiaryApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/time-use-diary"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val DATA_TYPE: String = "dataType"
        public const val END_DATE: String = "endDate"
        public const val ORGANIZATION_ID: String = "organizationId"
        public const val PARTICIPANT_ID: String = "participantId"
        public const val START_DATE: String = "startDate"
        public const val STUDY_ID: String = "studyId"
        public const val FILE_NAME: String = "fileName"

        public const val DOWNLOAD_PATH: String = "/download"
        public const val IDS_PATH: String = "/ids"
        public const val PARTICIPANTS_PATH: String = "/participants"
        public const val DATA_PATH: String = "/data"

        public const val ORGANIZATION_ID_PATH: String = "/{$ORGANIZATION_ID}"
        public const val PARTICIPANT_ID_PATH: String = "/{$PARTICIPANT_ID}"
        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"

        public const val STATUS_PATH: String = "/status"
        public const val STUDY_PATH: String = "/study"
        public const val PARTICIPANT_PATH: String = "/participant"
        public const val SETTINGS_PATH: String = "/settings"
    }

    // @formatter:off
    /**
     * Record responses of Time Use Diary survey
     *
     * @param studyId - Study ID
     * @param participantId - Participant ID
     * @param responses - a list of objects representing survey responses. Each object has at least 2 keys:
     * questionCode and response to represent a <Question, Answer> pair. Optional startDateTime and endDateTime
     * keys define a time range for some responses
     * @return a unique submission id
     *
     * @sample
     * [
            {
                "questionCode": ["bgTvNight"],
                "response": ["Yes, some of the time"]
            },
            {
                "questionCode": ["secondaryMediaActivity"],
                "response": [
                    "Watched video content (TV, movie, YouTube, etc.)",
                    "Played games (app, console game, etc.)",
                    "Video chat (Facetime, Zoom, etc.)",
                    "Communicated with others in another way (talked on phone, helped to write a text message, etc.)",
                    "Created content (recorded video, took photographs, etc.)"
            ],
                    "startDateTime": ["2021-12-16T18:00:18.422+03:00"],
                    "endDateTime": ["2021-12-16T15:00:18.422+03:00"]
            },
            {
                    "questionCode": ["primaryActivity"],
                    "response": ["Reading or listening to a story (paper book, eBook, audiobook, etc.)"],
                    "startDateTime": ["2021-12-16T19:00:18.422+03:00"],
                    "endDateTime": ["2021-12-16T18:00:18.422+03:00"]
            }
      ]
     */
    // @formatter:on

    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH)
    public fun submitTimeUseDiary(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Body responses: List<TimeUseDiaryResponse>
    ): UUID

    /**
     * Returns TUD survey submissionIds grouped by date for a single participant in a study within a date range
     *
     * @param studyId Study ID
     * @param participantId Participant ID
     * @param startDateTime lower bound submission date
     * @param endDateTime upper bound submission date
     * @return A set of submissionIds grouped by Date
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH)
    public fun getParticipantTUDSubmissionIdsByDate(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime,
    ): Map<OffsetDateTime, Set<UUID>>

    /**
     * Returns all TUD survey submissionIds grouped by date for a given date range and study
     *
     * @param studyId Study ID
     * @param startDateTime lower bound submission date
     * @param endDateTime upper bound submission date
     * @return A set of submissionIds grouped by Date
     */
    @GET(BASE + STUDY_ID_PATH + IDS_PATH)
    public fun getStudyTUDSubmissionIdsByDate(
        @Path(STUDY_ID) studyId: UUID,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime,
    ): Map<LocalDate, Set<UUID>>

    /**
     * Fetches TUD survey submissions for a given data range and study
     *
     * @param studyId Study ID
     * @param dataType type of data to fetch
     * @param startDateTime lower bound submission date
     * @param endDateTime upper bound submission date
     * @return An iterable data structure to be converted into a downloadable CSV file
     */
    @GET(BASE + STUDY_ID_PATH + DATA_PATH)
    public fun getStudyTUDSubmissions(
        @Path(STUDY_ID) studyId: UUID,
        @Query(DATA_TYPE) dataType: TimeUseDiaryDownloadDataType,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime
    ): Iterable<List<Map<String,Any>>>

    /**
     * Fetches TUD survey submissions for specified set of participants bound by an upper and lower offset datetime
     * @param studyId studyId
     * @param participantIds set of participants
     * @param startDateTime optional lower bound date
     * @param endDateTime optional upper bound date
     * @param dataType category of Time Use Diary data
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + DATA_PATH)
    public fun getParticipantsTudSubmissions(
        @Path(STUDY_ID) studyId: UUID,
        @Query(PARTICIPANT_ID) participantIds: Set<String>,
        @Query(DATA_TYPE) dataType: TimeUseDiaryDownloadDataType,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime
    ): Iterable<List<Map<String, Any>>>

    /**
     * Returns the study's Time Use Diary variant settings (OSU/Sherbrooke question set, clock
     * format, locale) so the participant-facing web diary can render the configured instrument.
     *
     * Participant-readable (unauthenticated, study-scoped, RLS-enforced); carries only the
     * non-sensitive variant flags — see [TimeUseDiarySettingsResponse].
     *
     * @param studyId Study ID
     */
    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH)
    public fun getTimeUseDiarySettings(
        @Path(STUDY_ID) studyId: UUID,
    ): TimeUseDiarySettingsResponse

}
