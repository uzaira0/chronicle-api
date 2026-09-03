package com.openlattice.chronicle.survey

import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.data.FileType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.OffsetDateTime
import java.util.*

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
// reason: public REST API contract (Retrofit) — each method is an endpoint; splitting the
// interface would fragment the survey/questionnaire/TUD API surface without behavior change
@Suppress("TooManyFunctions")
public interface SurveyApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/survey"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val ORGANIZATION_ID: String = "organizationId"
        public const val STUDY_ID: String = "studyId"
        public const val PARTICIPANT_ID: String = "participantId"
        public const val DATE: String = "date"
        public const val START_DATE: String = "startDate"
        public const val END_DATE: String = "endDate"
        public const val QUESTIONNAIRE_ID: String = "questionnaireId"
        public const val TYPE: String = "type"
        public const val FILE_NAME: String = "fileName"
        public const val THRESHOLD: String = "threshold"

        public const val PARTICIPANT_PATH: String = "/participant"
        public const val APP_USAGE_PATH: String = "/app-usage"
        public const val APP_USAGE_FREQUENCY_PATH: String = "/app-usage-frequency"
        public const val DEVICE_USAGE_PATH: String = "/device-usage"
        public const val QUESTIONNAIRE_PATH: String = "/questionnaire"
        public const val FILTERED_PATH: String = "/filtered"
        public const val DATA_PATH: String = "/data"

        public const val ORGANIZATION_ID_PATH: String = "/{$ORGANIZATION_ID}"
        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"
        public const val PARTICIPANT_ID_PATH: String = "/{$PARTICIPANT_ID}"
        public const val QUESTIONNAIRE_ID_PATH: String = "/{$QUESTIONNAIRE_ID}"
    }

    /**
     * Queries the device usage for a given study, participant, and time window.
     *
     * @param studyId the studyId
     * @param participantId the participant
     * @param startDateTime lower bound date (inclusive)
     * @param endDateTime upper bound date (exclusive)
     * @return
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + DEVICE_USAGE_PATH)
    public fun getDeviceUsageSurveyData(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime,
        @Query(THRESHOLD) thresholdInSeconds: Int? = 0,
    ): DeviceUsage

    /**
     * Returns the study's configured app-usage survey frequency (DAILY vs HOURLY) so the
     * participant web survey can render the matching variant. Participant-readable
     * (unauthenticated, study-scoped, RLS-enforced); defaults to DAILY when unset.
     *
     * @param studyId the studyId
     */
    @GET(BASE + STUDY_ID_PATH + APP_USAGE_FREQUENCY_PATH)
    public fun getAppUsageFrequency(
        @Path(STUDY_ID) studyId: UUID,
    ): AppUsageFrequencyResponse

    /**
     * Retrieve the app package filtered from the app usage survey.
     *
     * @param studyId Identifier of the study whose app usage survey filter is being retrieved.
     * @return A list of app package names that are filtered from the app usage survey.
     */
    @GET(BASE + STUDY_ID_PATH + FILTERED_PATH)
    public fun getAppsFilteredForStudyAppUsageSurvey(
        @Path(STUDY_ID) studyId: UUID,
    ): Collection<String>

    /**
     * Set the entire app usage survey filter all at once.
     *
     * @param studyId Identifier of the study whose app usage survey filter is being modified.
     * @param appPackages A set of app package names to filter from the app usage survey
     * @return HTTP OK with message success if request succeeds, other error code otherwise.
     */
    @PUT(BASE + STUDY_ID_PATH + FILTERED_PATH)
    public fun setAppsFilteredForStudyAppUsageSurvey(
        @Path(STUDY_ID) studyId: UUID,
        @Body appPackages: Set<String>,
    ): OK

    /**
     * Filter one or more app package names from the app usage survey.
     *
     * @param studyId Identifier of the study whose app usage survey filter is being modified.
     * @param appPackages A list of app package names to filter from the app usage survey.
     * @return HTTP OK with message success if request succeeds, other error code otherwise.
     */
    @PATCH(BASE + STUDY_ID_PATH + FILTERED_PATH)
    public fun filterAppForStudyAppUsageSurvey(
        @Path(STUDY_ID) studyId: UUID,
        @Body appPackages: Set<String>,
    ): OK

    /**
     * Allow one or more apps for the study app usage survey.
     *
     * @param studyId Identifier of the study whose app usage survey filter is being modified.
     * @param appPackages
     * @return HTTP OK with message success if request succeeds, other error code otherwise.
     */
    @HTTP(method = "DELETE", path = BASE + STUDY_ID_PATH + FILTERED_PATH)
    public fun allowAppForStudyAppUsageSurvey(
        @Path(STUDY_ID) studyId: UUID,
        @Body appPackages: Set<String>,
    ): OK

    /**
     * Queries the chronicle_usage_events table for usage events matching given studyId, participantId and date
     *
     * @param studyId the studyId
     * @param participantId the participant
     * @param startDateTime lower bound date (inclusive)
     * @param endDateTime upper bound date (exclusive)
     * @return a list of AppUsage objects where each object encapsulates
     * an app used at a specific timestamp in a specific timezone
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + APP_USAGE_PATH)
    public fun getAppUsageSurveyData(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime,
        @Query(THRESHOLD) thresholdInSeconds: Int? = 0,
    ): List<AppUsage>

    /**
     * Submit app usage survey responses for the specified participantId
     * Note that each instance of AppUsage(appPackageName, appLabel, timestamp) is considered a unique entity
     * and will not be overwritten if it already exists in storage
     *
     * @param studyId studyId
     * @param participantId participantId
     * @param surveyResponses a list of AppUsage Objects
     */
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + APP_USAGE_PATH)
    public fun submitAppUsageSurvey(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Body surveyResponses: List<AppUsage>,
    )

    /**
     * Create a new questionnaire
     *
     * @param studyId
     * @return questionnaire Id
     */
    @POST(BASE + STUDY_ID_PATH + QUESTIONNAIRE_PATH)
    public fun createQuestionnaire(
        @Path(STUDY_ID) studyId: UUID,
        @Body questionnaire: Questionnaire,
    ): UUID

    /**
     * Deletes a questionnaire. This does not delete any existing responses for the questionnaire
     *
     * @param studyId studyId
     * @param questionnaireId questionnaire id
     * @return success message if successful
     */
    @DELETE(BASE + STUDY_ID_PATH + QUESTIONNAIRE_PATH + QUESTIONNAIRE_ID_PATH)
    public fun deleteQuestionnaire(
        @Path(STUDY_ID) studyId: UUID,
        @Path(QUESTIONNAIRE_ID) questionnaireId: UUID,
    ): OK

    /**
     * Retrieves a questionnaire of given id
     *
     * @param studyId studyId
     * @param questionnaireId questionnaire id
     */
    @GET(BASE + STUDY_ID_PATH + QUESTIONNAIRE_PATH + QUESTIONNAIRE_ID_PATH)
    public fun getQuestionnaire(
        @Path(STUDY_ID) studyId: UUID,
        @Path(QUESTIONNAIRE_ID) questionnaireId: UUID,
    ): Questionnaire

    /**
     * Updates questionnaire details
     * @param studyId studyId
     * @param questionnaireId questionnaire id
     * @return "SUCCESS" if operation was successful
     */
    @PATCH(BASE + STUDY_ID_PATH + QUESTIONNAIRE_PATH + QUESTIONNAIRE_ID_PATH)
    public fun updateQuestionnaire(
        @Path(STUDY_ID) studyId: UUID,
        @Path(QUESTIONNAIRE_ID) questionnaireId: UUID,
        @Body update: QuestionnaireUpdate,
    ): OK

    /**
     * Retrieves all questionnaires associated with a study
     *
     * @param studyId studyId
     * @return a list of questionnaire objects
     */
    @GET(BASE + STUDY_ID_PATH + QUESTIONNAIRE_PATH)
    public fun getStudyQuestionnaires(
        @Path(STUDY_ID) studyId: UUID,
    ): List<Questionnaire>

    /**
     * Submit a participant's questionnaire responses
     *
     * @param studyId studyId
     * @param participantId participantId
     * @param questionnaireId questionnaire id
     * @return "success" message if submission was successful
     */
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + QUESTIONNAIRE_PATH + QUESTIONNAIRE_ID_PATH)
    public fun submitQuestionnaireResponses(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(QUESTIONNAIRE_ID) questionnaireId: UUID,
        @Body responses: List<QuestionnaireResponse>,
    ): OK

    /**
     * Fetches all responses to a given questionnaire and writes to specified file type
     *
     * @param studyId studyId
     * @param questionnaireId questionnaire Id
     * @param fileType type of file to write to
     */
    @GET(BASE + STUDY_ID_PATH + QUESTIONNAIRE_PATH + QUESTIONNAIRE_ID_PATH + DATA_PATH)
    public fun getQuestionnaireResponses(
        @Path(STUDY_ID) studyId: UUID,
        @Path(QUESTIONNAIRE_ID) questionnaireId: UUID,
        @Query(value = TYPE) fileType: FileType,
    ): Iterable<Map<String, Any>>
}
