package com.openlattice.chronicle.study


import com.openlattice.chronicle.android.AndroidDeviceSensorAvailability
import com.openlattice.chronicle.android.AndroidSensorSample
import com.openlattice.chronicle.android.AndroidSensorSetting
import com.openlattice.chronicle.android.ChronicleData
import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.collection.AndroidInteractionEvent
import com.openlattice.chronicle.collection.BatterySample
import com.openlattice.chronicle.crypto.EncryptedEnvelope
import com.openlattice.chronicle.collection.CollectionAcknowledgment
import com.openlattice.chronicle.collection.CollectionAcknowledgmentEntry
import com.openlattice.chronicle.data.ParticipationStatus
import com.openlattice.chronicle.organizations.ChronicleDataCollectionSettings
import com.openlattice.chronicle.participants.Participant
import com.openlattice.chronicle.participants.ParticipantStats
import com.openlattice.chronicle.sensorkit.SensorDataSample
import com.openlattice.chronicle.sensorkit.SensorType
import com.openlattice.chronicle.sources.SourceDevice
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
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
        public const val SOURCE_DEVICE_ID: String = "sourceDeviceId"
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
        public const val SOURCE_DEVICE_ID_PATH: String = "/{$SOURCE_DEVICE_ID}"
        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"
        public const val PARTICIPANT_PATH: String = "/participant"
        public const val PARTICIPANTS_PATH: String = "/participants"
        public const val ORGANIZATION_PATH: String = "/organization"
        public const val UPLOAD_PATH: String = "/upload"
        public const val SENSORS_PATH: String = "/sensors"
        public const val SETTINGS_PATH: String = "/settings"
        public const val PERMISSIONS_PATH: String = "/permissions"
        public const val SETTING_TYPE_PATH: String = "/type/{$SETTING_TYPE}"
        public const val STATS_PATH: String = "/stats"
        public const val STATUS_PATH: String = "/status"
        public const val IOS_PATH: String = "/ios"
        public const val ANDROID_PATH: String = "/android"
        public const val AVAILABILITY_PATH: String = "/availability"
        public const val BATTERY_PATH: String = "/battery"
        public const val INTERACTION_PATH: String = "/interaction"
        public const val ENCRYPTED_PATH: String = "/encrypted"
        public const val RETRIEVE: String = "retrieve"
        public const val DATA_COLLECTION: String = "/data-collection/"
        public const val DEVICES_PATH: String = "/devices"
        public const val ANNOTATIONS_PATH: String = "/annotations"
        public const val AUDIT_PATH: String = "/audit"
        public const val COLLECTION_ACK_PATH: String = "/collection-ack"
        public const val ACKNOWLEDGMENTS_PATH: String = "/acknowledgments"
        public const val UPLOAD_STATUS_PATH: String = "/upload-status"
    }


    /**
     * Enrolls a participant's mobile app-device instance in a study.
     *
     * The legacy `sourceDeviceId` path parameter is now treated as Chronicle's
     * app-private device-instance ID. It is not a hardware identifier. It remains
     * stable while app data is intact, so re-enrollment of the same app instance
     * maps to one deduped device row while each enrollment event is recorded
     * separately.
     *
     * @param studyId        The id of the study.
     * @param participantId  The participant id associated with the app instance.
     * @param sourceDeviceId Chronicle app-instance ID from the mobile app.
     * @param sourceDevice   Sanitized mobile device metadata.
     * @return The deduped server device UUID, optional enrollment-event UUID, and optional API key.
     */
    @Deprecated("Use v4 enrollV4 instead, which accepts device ID via header")
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + SOURCE_DEVICE_ID_PATH + ENROLL_PATH)
    public fun enroll(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) sourceDeviceId: String,
        @Body sourceDevice: SourceDevice,
    ): EnrollmentResponse

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
     * Updating SensorKit data collection for a study requires admin permission due to Apple restrictions.
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
     * Retrieves the study permissions for a study.
     *
     * Requires owner permissions to read the ACL.
     */
    @GET(BASE + STUDY_ID_PATH + PERMISSIONS_PATH)
    public fun getStudyPermissions(@Path(STUDY_ID) studyId: UUID): StudyPermissions

    /**
     * Updates the permissions for a study.
     *
     * Requires owner permissions to modify the ACL.
     */
    @POST(BASE + STUDY_ID_PATH + PERMISSIONS_PATH)
    public fun updateStudyPermissions(
        @Path(STUDY_ID) studyId: UUID,
        @Body permissionsUpdate: StudyPermissionsUpdate
    ): StudyPermissions

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
     * Uploads sensor data from iOS device
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceDeviceId A unique id obtained from https://developer.apple.com/documentation/uikit/uidevice/1620059-identifierforvendor
     * @param data A list of SensorDataSample objects.
     * @return number of rows written
     */
    @Deprecated("Use v4 uploadSensorDataV4 instead")
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + IOS_PATH + SOURCE_DEVICE_ID_PATH)
    public fun uploadSensorData(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) sourceDeviceId: String,
        @Body data: List<SensorDataSample>,
    ): Int

    /**
     * Uploads sensor data from iOS device
     *
     * @param studyId - studyId
     * @param dataCollectionSettings - A list of SensorDataSample objects.
     * @return number of rows written
     */
    @PUT(BASE + STUDY_ID_PATH + DATA_COLLECTION)
    public fun setChronicleDataCollectionSettings(
        @Path(STUDY_ID) studyId: UUID,
        @Body dataCollectionSettings: ChronicleDataCollectionSettings,
    ): OK

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
     * Fetches sensors configured for a study. This is used by iOS devices to retrieve the sensors enabled for a particular study.
     *
     * @param studyId studyId
     * @return all sensor types for given study
     */
    @Deprecated("Prefer getStudySetting, this is left in for app compat.")
    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH + SENSORS_PATH)
    public fun getStudySensors(
        @Path(STUDY_ID) studyId: UUID,
    ): Set<SensorType>

    /** Upload usage event data from android devices
     * @param studyId studyId
     * @param participantId participantId
     * @param datasourceId device id unique to each combination of app-signing key, user and device
     * @param data A list of usage event objects to write. Each object encapsulates an instance of
     * android's UsageEvents.Event with properties such as package name, timestamp and event type
     * ref: https://developer.android.com/reference/android/app/usage/UsageEvents.Event
     */
    @Deprecated("Use v4 uploadAndroidUsageEventDataV4 instead")
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SOURCE_DEVICE_ID_PATH)
    public fun uploadAndroidUsageEventData(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) datasourceId: String,
        @Body data: ChronicleData,
    ): Int

    /**
     * Uploads hardware sensor data from Android devices.
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceDeviceId The device id.
     * @param data A list of AndroidSensorSample objects.
     * @return number of rows written
     */
    @Deprecated("Use v4 uploadAndroidSensorDataV4 instead")
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SOURCE_DEVICE_ID_PATH + SENSORS_PATH)
    public fun uploadAndroidSensorData(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) sourceDeviceId: String,
        @Body data: List<AndroidSensorSample>,
    ): Int

    /**
     * Uploads a batch of battery-telemetry samples collected by the Android
     * `battery_telemetry` collection module.
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceDeviceId The device id.
     * @param data A list of [com.openlattice.chronicle.collection.BatterySample] objects.
     * @return number of samples uploaded
     */
    @Deprecated("Use v4 uploadBatteryTelemetryV4 instead")
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SOURCE_DEVICE_ID_PATH + BATTERY_PATH)
    public fun uploadBatteryTelemetry(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) sourceDeviceId: String,
        @Body data: List<BatterySample>,
    ): Int

    /**
     * Uploads a batch of interaction-salience events collected by the Android
     * `interaction_events` collection module. Content-free metadata (grid cell + role +
     * foreground package), never element text.
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceDeviceId The device id.
     * @param data A list of [com.openlattice.chronicle.collection.AndroidInteractionEvent] objects.
     * @return number of events uploaded
     */
    @Deprecated("Use v4 uploadInteractionEventsV4 instead")
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SOURCE_DEVICE_ID_PATH + INTERACTION_PATH)
    public fun uploadInteractionEvents(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) sourceDeviceId: String,
        @Body data: List<AndroidInteractionEvent>,
    ): Int

    /**
     * Retrieves Android sensor settings for a study.
     *
     * @param studyId The id of the study.
     * @return The Android sensor settings.
     */
    @GET(BASE + STUDY_ID_PATH + SETTINGS_PATH + "/type/AndroidSensor")
    public fun getAndroidSensorSettings(
        @Path(STUDY_ID) studyId: UUID,
    ): AndroidSensorSetting

    /**
     * Reports which sensors are available and unavailable on a device.
     */
    @POST(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH
        + ANDROID_PATH + SOURCE_DEVICE_ID_PATH + SENSORS_PATH + AVAILABILITY_PATH)
    public fun reportAndroidSensorAvailability(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Path(SOURCE_DEVICE_ID) sourceDeviceId: String,
        @Body availability: AndroidDeviceSensorAvailability,
    ): Int

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
    public fun getStudyDevices(@Path(STUDY_ID) studyId: UUID): Map<String, List<Map<String, Any>>>

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

    /**
     * Retrieves server-observed iOS upload/commit state for participants in a study.
     *
     * This reports rows already moved into long-term iOS sensor storage plus any
     * iOS upload-buffer records still waiting for the background mover.
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + IOS_PATH + UPLOAD_STATUS_PATH)
    public fun getIosUploadStatus(
        @Path(STUDY_ID) studyId: UUID,
    ): Map<String, IosUploadStatus>


    /**
     * Retrieve data of specified type associated with a set of participants in a study, bounded by a lower and upper offset datetime.
     * The interpretation of the date range depends on the context in which this endpoint is invoked.
     * @param studyId studyId
     * @param dataType one of  UsageEvents, Preprocessed,AppUsageSurvey, IOSSensor
     * @param participantIds ids of participants
     * @param startDateTime an optional lower bound date
     * @param endDateTime an optional upper bound date
     */
    @GET(BASE + STUDY_ID_PATH + PARTICIPANTS_PATH + DATA_PATH)
    public fun getParticipantsData(
        @Path(STUDY_ID) studyId: UUID,
        @Query(DATA_TYPE) dataType: ParticipantDataType,
        @Query(PARTICIPANT_ID) participantIds: Set<String>,
        @Query(START_DATE) startDateTime: OffsetDateTime,
        @Query(END_DATE) endDateTime: OffsetDateTime,
    ): Iterable<Map<String, Any>>

    /**
     * Updates participation status of participant in specified study
     * @param studyId studyId
     * @param participantId participantId
     * @param participationStatus new status value
     */
    @PATCH(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + STATUS_PATH)
    public fun updateParticipationStatus(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Query(PARTICIPATION_STATUS) participationStatus: ParticipationStatus,
    ): OK

    /**
     * Updates participant annotations (notes and tags).
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param annotations A map containing "participantNotes" (String) and/or "participantTags" (List<String>).
     */
    @PATCH(BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANNOTATIONS_PATH)
    public fun updateParticipantAnnotations(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Body annotations: Map<String, @JvmSuppressWildcards Any?>,
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
     * collection module (design `docs/COLLECTION-LOOP-CLOSURE-DESIGN.md` §5.3).
     * Surfaced in the web audit view alongside [getStudySettingsAudit]; kept as a
     * sibling feed so the immutable settings-audit schema stays unchanged (§11).
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

    @Suppress("DEPRECATION")
    @GET(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ENROLLMENT_PREVIEW_PATH)
    public fun getEnrollmentPreviewV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Enrollment-Code") enrollmentCode: String,
    ): EnrollmentPreviewResponse

    // Every parameter is a separately authenticated part of the stable enrollment wire contract.
    @Suppress("DEPRECATION", "LongParameterList")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ENROLL_PATH)
    public fun enrollV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body datasource: SourceDevice,
        @Header("X-Chronicle-Enrollment-Code") enrollmentCode: String? = null,
        @Header("X-Chronicle-Manifest-Digest") manifestDigest: String? = null,
        @Header("X-Chronicle-Enrollment-Attempt-Id") enrollmentAttemptId: String? = null,
        @Header("X-Chronicle-Proposed-Api-Key") proposedApiKey: String? = null,
    ): EnrollmentResponse {
        return enroll(studyId, participantId, sourceDeviceId, datasource)
    }

    @Suppress("DEPRECATION")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH)
    public fun uploadAndroidUsageEventDataV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body data: ChronicleData,
    ): Int {
        return uploadAndroidUsageEventData(studyId, participantId, sourceDeviceId, data)
    }

    @Suppress("DEPRECATION")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + SENSORS_PATH)
    public fun uploadAndroidSensorDataV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body data: List<AndroidSensorSample>,
    ): Int {
        return uploadAndroidSensorData(studyId, participantId, sourceDeviceId, data)
    }

    @Suppress("DEPRECATION")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + BATTERY_PATH)
    public fun uploadBatteryTelemetryV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body data: List<BatterySample>,
    ): Int {
        return uploadBatteryTelemetry(studyId, participantId, sourceDeviceId, data)
    }

    @Suppress("DEPRECATION")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + INTERACTION_PATH)
    public fun uploadInteractionEventsV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body data: List<AndroidInteractionEvent>,
    ): Int {
        return uploadInteractionEvents(studyId, participantId, sourceDeviceId, data)
    }

    @Suppress("DEPRECATION")
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + IOS_PATH)
    public fun uploadSensorDataV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body data: List<SensorDataSample>,
    ): Int {
        return uploadSensorData(studyId, participantId, sourceDeviceId, data)
    }

    /**
     * Records a participant's on-device acknowledgment of one or more newly-enabled
     * collection modules — the device-side close of the consent loop (design
     * `docs/COLLECTION-LOOP-CLOSURE-DESIGN.md` §5.2). A module enabled by a researcher
     * stays in `PENDING_ACK` (not collecting) until the participant acknowledges it
     * on-device, at which point the app calls this endpoint.
     *
     * This is a new endpoint with no v3 counterpart. Auth (`X-Api-Key`) and device id
     * (`X-Chronicle-Device-Id`) ride the standard v4 android headers. The server
     * stamps its own authoritative receipt time when persisting; the body's
     * [CollectionAcknowledgment.acknowledgedAt] is advisory only.
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceDeviceId The device id (via header).
     * @param acknowledgment The modules being acknowledged plus device metadata.
     */
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + COLLECTION_ACK_PATH)
    public fun reportCollectionAcknowledgmentV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body acknowledgment: CollectionAcknowledgment,
    ): OK

    /**
     * Uploads a batch of envelope-encrypted payloads (HIPAA-2028 W2). Each
     * [EncryptedEnvelope] is one sealed batch of one stream (sensor / usage / battery);
     * the server stores them blind (it never holds the study private key) and returns the
     * count accepted. New v4-only endpoint; auth (`X-Api-Key`) and device id
     * (`X-Chronicle-Device-Id`) ride the standard v4 android headers.
     *
     * @param studyId The id of the study.
     * @param participantId The id of the participant.
     * @param sourceDeviceId The device id (via header).
     * @param data The sealed envelopes to store.
     */
    @POST(V4_BASE + STUDY_ID_PATH + PARTICIPANT_PATH + PARTICIPANT_ID_PATH + ANDROID_PATH + ENCRYPTED_PATH)
    public fun uploadAndroidEncryptedDataV4(
        @Path(STUDY_ID) studyId: UUID,
        @Path(PARTICIPANT_ID) participantId: String,
        @Header("X-Chronicle-Device-Id") sourceDeviceId: String,
        @Body data: List<EncryptedEnvelope>,
    ): Int
}
