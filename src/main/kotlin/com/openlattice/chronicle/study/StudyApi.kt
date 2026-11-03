package com.openlattice.chronicle.study

import com.openlattice.chronicle.generated.ChronicleHeaders
import com.openlattice.chronicle.EditRevisionApi

import com.openlattice.chronicle.android.AndroidDeviceSensorAvailability
import com.openlattice.chronicle.collection.CollectionUploadEvidence
import com.openlattice.chronicle.android.UploadReceipt
import com.openlattice.chronicle.android.AndroidSensorSample
import com.openlattice.chronicle.android.ChronicleData
import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.collection.AndroidInteractionEvent
import com.openlattice.chronicle.collection.BatterySample
import com.openlattice.chronicle.collection.CollectionAcknowledgment
import com.openlattice.chronicle.collection.CollectionAcknowledgmentEntry
import com.openlattice.chronicle.data.ParticipationStatus
import com.openlattice.chronicle.participants.Participant
import com.openlattice.chronicle.participants.ParticipantStats
import com.openlattice.chronicle.sources.SourceDevice
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

/**
 * @author Solomon Tang <solomon@openlattice.com>
 */
// reason: Retrofit HTTP contract — splitting would change the client interface surface for no behavior gain
@Suppress("TooManyFunctions")
public interface StudyApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/study"
        public const val V4_CONTROLLER: String = "/v4/study"
        public const val BASE: String = SERVICE + CONTROLLER
        public const val V4_BASE: String = SERVICE + V4_CONTROLLER

        public const val ORGANIZATION_ID: String = "organizationId"
        public const val STUDY_ID: String = "studyId"
        public const val PARTICIPANT_ID: String = "participantId"
        public const val START_DATE: String = "startDate"
        public const val END_DATE: String = "endDate"
        public const val DATA_TYPE: String = "dataType"
        public const val RESPONSE_TYPE: String = "responseType"
        public const val CATEGORY: String = "category"
        public const val FILE_NAME: String = "fileName"
        public const val PARTICIPATION_STATUS: String = "participationStatus"
        public const val SETTING_TYPE: String = "settingType"

        public const val VERIFY_PATH: String = "/verify"
        public const val DATA_PATH: String = "/data"
        public const val ENROLL_PATH: String = "/enroll"
        public const val ENROLLMENT_PREVIEW_PATH: String = "/enrollment-preview"
        public const val ORGANIZATION_ID_PATH: String = "/{$ORGANIZATION_ID}"
        public const val PARTICIPANT_ID_PATH: String = "/{$PARTICIPANT_ID}"
        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"
        public const val PARTICIPANT_PATH: String = "/participant"
        public const val PARTICIPANTS_PATH: String = "/participants"
        public const val ORGANIZATION_PATH: String = "/organization"
        public const val UPLOAD_PATH: String = "/upload"
        public const val SENSORS_PATH: String = "/sensors"
        public const val RECEIPT_PATH: String = "/receipt"
        public const val SETTINGS_PATH: String = "/settings"
        public const val SETTING_TYPE_PATH: String = "/type/{$SETTING_TYPE}"
        public const val STATS_PATH: String = "/stats"
        public const val STATUS_PATH: String = "/status"
        public const val IOS_PATH: String = "/ios"
        public const val ANDROID_PATH: String = "/android"
        public const val AVAILABILITY_PATH: String = "/availability"
        public const val BATTERY_PATH: String = "/battery"
        public const val INTERACTION_PATH: String = "/interaction"
        public const val RETRIEVE: String = "retrieve"
        public const val DATA_COLLECTION: String = "/data-collection"
        public const val DEVICES_PATH: String = "/devices"
        public const val ANNOTATIONS_PATH: String = "/annotations"
        public const val AUDIT_PATH: String = "/audit"
        public const val COLLECTION_ACK_PATH: String = "/collection-ack"
        public const val ACKNOWLEDGMENTS_PATH: String = "/acknowledgments"
        public const val UPLOAD_STATUS_PATH: String = "/upload-status"
        public const val DIAGNOSTICS_PATH: String = "/diagnostics"

        public const val AUDIO_ACTIVITY_PATH: String = "/audio-activity"
        public const val AUDIO_CONTENT_PATH: String = "/audio-content"
        public const val NOTIFICATION_ACTIVITY_PATH: String = "/notification-activity"
        public const val SLEEP_PATH: String = "/sleep"
        public const val ACTIVITY_RECOGNITION_PATH: String = "/activity-recognition"
        public const val HEALTH_CONNECT_PATH: String = "/health-connect"
        public const val CONNECTIVITY_STATE_PATH: String = "/connectivity-state"
        public const val APP_NETWORK_USAGE_PATH: String = "/app-network-usage"
        public const val DEVICE_SETTINGS_PATH: String = "/device-settings"
        public const val UPLOAD_DIAGNOSTICS_PATH: String = "/upload-diagnostics"
        public const val SCREEN_TIME_PATH: String = "/screen-time"

        /** Static platform resources shared by routing and privacy-safe path shaping. */
        public val PLATFORM_DATA_PATHS: Set<String> = setOf(
            AVAILABILITY_PATH, DATA_PATH, SENSORS_PATH,
            STATUS_PATH, UPLOAD_PATH, RECEIPT_PATH,
            COLLECTION_ACK_PATH, BATTERY_PATH, INTERACTION_PATH,
            AUDIO_ACTIVITY_PATH, AUDIO_CONTENT_PATH,
            NOTIFICATION_ACTIVITY_PATH, SLEEP_PATH, ACTIVITY_RECOGNITION_PATH,
            HEALTH_CONNECT_PATH, CONNECTIVITY_STATE_PATH, APP_NETWORK_USAGE_PATH,
            DEVICE_SETTINGS_PATH, UPLOAD_DIAGNOSTICS_PATH,
            SCREEN_TIME_PATH,
        )
    }

    /**
     * Creates a new study. A study may be associated with one or more organizations and will be assigned an id
     * at creation time.
     *
     * @param study The study to create.
     * @return The id assigned to the study.
     * etc.
     */
    @POST(BASE)
    public fun createStudy(@Body study: Study): UUID

    /**
     * Retrieves an existing study based on id.
     *
     * @param studyId The id of the study to retrieve.
     * @return The study corresponding to the provided id.
     * etc.
     */
    @GET(BASE + STUDY_ID_PATH)
    public fun getStudy(@Path(STUDY_ID) studyId: UUID): Study

    /**
     * Retrieves all studies that belong to an organization
     *
     * @param organizationId The id of the organization to retrieve from.
     * @return A list of studies that belong to the provided organization.
     */
    @GET(BASE + ORGANIZATION_PATH + ORGANIZATION_ID_PATH)
    public fun getOrgStudies(
        @Path(ORGANIZATION_ID) organizationId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): List<Study>

    /**
     * Updates an existing study based on id
     * @param studyId The id of the study to update.
     * @param study The changes to the study. Excludes non-user specifiable fields such as studyId, createdAt, updatedAt
     * @param retrieve Set to true to retrieve the updated view of the study.
     * Does not accept changes to associated organizations.
     */
    @PATCH(BASE + STUDY_ID_PATH)
    public fun updateStudy(
        @Path(STUDY_ID) studyId: UUID,
        @Body study: StudyUpdate,
        @Query(RETRIEVE) retrieve: Boolean = false,
    ): Study?

    /**
     * Updates study settings for a study.
     *
     * Only [settingType] is touched: the server merges the delta into the row-locked authoritative
     * settings map, so a write of one type never rewrites another type from a client-supplied merge.
     *
     * Optimistic concurrency: the settings GET endpoints return the study's `settingsRevision` as a
     * quoted strong `ETag`. Send it back as `If-Match: "<revision>"` to make the write conditional
     * on the state that was read; a stale revision is rejected with HTTP 412 and a
     * [StudySettingsPreconditionFailure] body carrying the current revision and settings. Omitting
     * the header (or sending `*`) skips the check, preserving behaviour for existing mobile/iOS
     * clients. Every successful settings write increments the revision and returns it in `ETag`.
     *
     * @param studyId The id of the study to update.
     * @param settingType The setting type to update.
     * @param settings The new settings for the study.
     */
    @PATCH(BASE + STUDY_ID_PATH + SETTINGS_PATH + SETTING_TYPE_PATH)
    public fun updateStudySettings(
        @Path(STUDY_ID) studyId: UUID,
        @Path(SETTING_TYPE) settingType: StudySettingType,
        @Body settings: StudySetting,
    ): OK

    /**
     * Deletes an existing study, its associations to any organizations, and removes
     * all participants by study id. Creates 3 jobs to delete all usage data, time use diary submissions
     * and app usage surveys for that study.
     *
     * @param studyId The id of the study to be destroyed.
     * @return The ids of the background jobs created to delete data related to the study
     */
    @DELETE(BASE + STUDY_ID_PATH)
    public fun destroyStudy(@Path(STUDY_ID) studyId: UUID): Iterable<UUID>

    /**
     * Removes participants from a study by participant ids. Creates 3 jobs to delete usage data, time use diary submissions,
     * and app usage surveys for all removed participants.
     *
     * @param studyId The id of the study
     * @param participantIds a collection of participant ids to be deleted
     * @return the ids of the background jobs created to delete data related to the deleted participants
     */
    @HTTP(
        path = BASE + STUDY_ID_PATH + PARTICIPANTS_PATH,
        method = "DELETE",
        hasBody = true
    )
    public fun deleteStudyParticipants(
        @Path(STUDY_ID) studyId: UUID,
        @Body participantIds: Set<String>,
    ): Iterable<UUID>

    /**
     * Registers a participant in a study and creates the corresponding candidate if they do not exist.
     * @param participant The participant to register
     * @return The id of the candidate that is created.
     */
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH)
    public fun registerParticipant(
        @Path(STUDY_ID) studyId: UUID,
        @Body participant: Participant,
    ): UUID

    /**
     * Returns the settings for a given study
     * This endpoint expects the caller to know the value type(s)
     * @param studyId studyId
     */
    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH)
    public fun getStudySettings(
        @Path(STUDY_ID) studyId: UUID,
    ): Map<StudySettingType, StudySetting>

    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH + SETTING_TYPE_PATH)
    public fun getStudySetting(
        @Path(STUDY_ID) studyId: UUID,
        @Path(SETTING_TYPE) settingsKey: StudySettingType,
    ): StudySetting

    /**
     * Retrieves app-device instances in a study, grouped by participant ID.
     *
     * Each returned instance includes an enrollment-event trail so clients can
     * distinguish repeated enrollment of one app instance from multiple app
     * instances for one participant.
     *
     * @param studyId The id of the study.
     * @return A map of participantId to list of app-device instance info maps.
     */
    @GET(BASE + STUDY_ID_PATH + DEVICES_PATH)
    public fun getStudyDevices(
        @Path(STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): Map<String, List<Map<String, Any>>>

    /**
     * Retrieves sensor availability reports for all devices in a study.
     *
     * @param studyId The id of the study.
     * @return A list of sensor availability records.
     */
    @GET(BASE + STUDY_ID_PATH + ANDROID_PATH + SENSORS_PATH + AVAILABILITY_PATH)
    public fun getStudySensorAvailability(@Path(STUDY_ID) studyId: UUID): List<AndroidDeviceSensorAvailability>

    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH)
    public fun getStudyParticipants(
        @Path(STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): Iterable<Participant>

    /**
     * Verifies that participant is in a study
     * @param studyId studyId
     * @param participantId participantId
     * @return true if the participant is in the study
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + VERIFY_PATH)
    public fun isKnownParticipant(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
    ): Boolean

    @GET(BASE)
    public fun getAllStudies(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): Iterable<Study>

    /**
     * Retrieves stats of participant in a study
     * @param studyId studyId
     * @return a map of participantId to stats object.
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + STATS_PATH)
    public fun getParticipantStats(
        @Path(STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): Map<String, ParticipantStats>

    /** Retrieves the stored iOS Screen Time state of each participant in a study. */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + IOS_PATH + UPLOAD_STATUS_PATH)
    public fun getIosUploadStatus(
        @Path(STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): Map<String, IosUploadStatus>

    /** Full keyset-paginated upload-diagnostic and data-quality alert history. */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + ANDROID_PATH + DIAGNOSTICS_PATH)
    public fun getAndroidDiagnostics(
        @Path(STUDY_ID) studyId: UUID,
        @Query("participantId") participantId: String? = null,
        @Query("randomDeviceId") randomDeviceId: UUID? = null,
        @Query("from") fromDay: LocalDate? = null,
        @Query("to") toDay: LocalDate? = null,
        @Query("moduleFamily") moduleFamily: String? = null,
        @Query("issueCode") issueCode: String? = null,
        @Query("cursor") cursor: String? = null,
        @Query("limit") limit: Int = 50,
    ): AndroidDiagnosticsPage

    /**
     * Retrieve data of specified type associated with a set of participants in a study, bounded by a lower and upper offset datetime.
     * The interpretation of the date range depends on the context in which this endpoint is invoked.
     * @param studyId studyId
     * @param dataType the [ParticipantDataType] to read
     * @param participantIds ids of participants
     * @param startDateTime inclusive lower bound; null starts at the earliest retained row
     * @param endDateTime exclusive upper bound; null runs to the latest retained row
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + DATA_PATH + "?responseType=json")
    public fun getParticipantsData(
        @Path(STUDY_ID) studyId: UUID,
        @Query(DATA_TYPE) dataType: ParticipantDataType,
        @Query(PARTICIPANT_ID) participantIds: Set<String>,
        @Query(START_DATE) startDateTime: OffsetDateTime? = null,
        @Query(END_DATE) endDateTime: OffsetDateTime? = null,
    ): Iterable<Map<String, Any>>

    /**
     * Updates participation status of participant in specified study
     * @param studyId studyId
     * @param participantId participantId
     * @param participationStatus new status value
     * @param revision `"<statusRevision>"` of the participant as read; missing is 428, stale is 412
     */
    @PATCH(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + STATUS_PATH)
    public fun updateParticipationStatus(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Query(PARTICIPATION_STATUS) participationStatus: ParticipationStatus,
        @Header(EditRevisionApi.IF_MATCH) revision: String?,
    ): OK

    /**
     * Updates participant annotations (notes and tags).
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param annotations Omitted fields are unchanged; null clears a field.
     */
    @PATCH(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANNOTATIONS_PATH)
    public fun updateParticipantAnnotations(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Body annotations: ParticipantAnnotationsUpdate,
        @Header(EditRevisionApi.IF_MATCH) revision: String,
    ): OK

    /**
     * Retrieves the audit trail for study settings changes.
     *
     * @param studyId The id of the study.
     * @param limit Maximum number of audit entries to return.
     * @param offset Number of entries to skip for pagination.
     * @return A list of audit entries ordered by most recent first.
     */
    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH + AUDIT_PATH)
    public fun getStudySettingsAudit(
        @Path(STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): List<StudySettingsAuditEntry>

    /**
     * Retrieves the participant collection-acknowledgment trail for a study — the
     * append-only record of when each participant acknowledged each newly-enabled
     * collection module.
     * Surfaced in the web audit view alongside [getStudySettingsAudit]; kept as a
     * sibling feed so the immutable settings-audit schema stays unchanged.
     *
     * @param studyId The id of the study.
     * @param limit Maximum number of acknowledgment entries to return.
     * @param offset Number of entries to skip for pagination.
     * @return A list of acknowledgment entries ordered by most recent first.
     */
    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH + ACKNOWLEDGMENTS_PATH)
    public fun getStudyCollectionAcknowledgments(
        @Path(STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): List<CollectionAcknowledgmentEntry>

    @GET(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ENROLLMENT_PREVIEW_PATH)
    public fun getEnrollmentPreviewV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.ENROLLMENT_CODE_HEADER) enrollmentCode: String,
    ): EnrollmentPreviewResponse

    // Every parameter is a separately authenticated part of the stable enrollment wire contract.
    @Suppress("LongParameterList")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ENROLL_PATH)
    public fun enrollV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body datasource: SourceDevice,
        @Header(ChronicleHeaders.ENROLLMENT_CODE_HEADER) enrollmentCode: String? = null,
        @Header(ChronicleHeaders.MANIFEST_DIGEST_HEADER) manifestDigest: String? = null,
        @Header(ChronicleHeaders.ENROLLMENT_ATTEMPT_ID_HEADER) enrollmentAttemptId: String? = null,
        @Header(ChronicleHeaders.PROPOSED_API_KEY_HEADER) proposedApiKey: String? = null,
    ): EnrollmentResponse

    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH)
    public fun uploadAndroidUsageEventDataV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body data: ChronicleData,
        @Header(CollectionUploadEvidence.SETTINGS_VERSION_HEADER) settingsVersion: Int? = null,
        @Header(CollectionUploadEvidence.DECISION_SEQ_HEADER) decisionSeq: Long? = null,
    ): UploadReceipt

    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SENSORS_PATH)
    public fun uploadAndroidSensorDataV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body data: List<AndroidSensorSample>,
    ): Int

    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SENSORS_PATH + RECEIPT_PATH)
    public fun uploadAndroidSensorDataReceiptV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body data: List<AndroidSensorSample>,
        @Header(CollectionUploadEvidence.SETTINGS_VERSION_HEADER) settingsVersion: Int? = null,
        @Header(CollectionUploadEvidence.DECISION_SEQ_HEADER) decisionSeq: Long? = null,
    ): UploadReceipt

    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + BATTERY_PATH)
    public fun uploadBatteryTelemetryV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body data: List<BatterySample>,
        @Header(CollectionUploadEvidence.SETTINGS_VERSION_HEADER) settingsVersion: Int? = null,
        @Header(CollectionUploadEvidence.DECISION_SEQ_HEADER) decisionSeq: Long? = null,
    ): UploadReceipt

    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + INTERACTION_PATH)
    public fun uploadInteractionEventsV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body data: List<AndroidInteractionEvent>,
    ): Int

    /**
     * Records a participant's on-device acknowledgment of one or more newly-enabled
     * collection modules, the device-side close of the consent loop. A module enabled by a researcher
     * stays in `PENDING_ACK` (not collecting) until the participant acknowledges it
     * on-device, at which point the app calls this endpoint.
     *
     * This endpoint has no v3 counterpart. Auth (`X-Api-Key`) and random device id
     * (`X-Chronicle-Random-Device-Id`) ride the standard v4 android headers. The server
     * stamps its own authoritative receipt time when persisting; the body's
     * [CollectionAcknowledgment.acknowledgedAt] is advisory only.
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceRandomDeviceId The random device id (via header).
     * @param acknowledgment The modules being acknowledged plus device metadata.
     */
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + COLLECTION_ACK_PATH)
    public fun reportCollectionAcknowledgmentV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header(ChronicleHeaders.RANDOM_DEVICE_ID_HEADER) sourceRandomDeviceId: String,
        @Body acknowledgment: CollectionAcknowledgment,
    ): OK
}
