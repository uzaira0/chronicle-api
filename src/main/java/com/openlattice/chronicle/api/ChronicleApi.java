package com.openlattice.chronicle.api;

import com.google.common.base.Optional;
import com.google.common.collect.SetMultimap;
import com.openlattice.chronicle.data.ChronicleAppsUsageDetails;
import com.openlattice.chronicle.data.LegacyChronicleQuestionnaire;
import com.openlattice.chronicle.data.ParticipationStatus;
import com.openlattice.chronicle.sources.SourceDevice;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public interface ChronicleApi {
    String SERVICE    = "/chronicle";
    String CONTROLLER = "/v2";
    String BASE       = SERVICE + CONTROLLER;

    String APP_NAME = "appName";
    String DATASOURCE_ID   = "datasourceId";
    String DATE            = "date";
    String ENTITY_KEY_ID   = "entityKeyId";
    String PARTICIPANT_ID  = "participantId";
    String STUDY_ID        = "studyId";
    String ORGANIZATION_ID = "organizationId";

    String APPS_PATH              = "/apps";
    String EDM_PATH               = "/edm";
    String ENROLL_PATH            = "/enroll";
    String ENROLLMENT_STATUS_PATH = "/status";
    String NOTIFICATIONS_PATH     = "/notifications";
    String QUESTIONNAIRE_PATH     = "/questionnaire";
    String QUESTIONNAIRES_PATH    = "/questionnaires";
    String SETTINGS_PATH          = "/settings";
    String STATUS_PATH            = "/status";
    String TIME_USE_DIARY         = "/time-use-diary";
    String UPLOAD_PATH            = "/upload";

    String DATASOURCE_ID_PATH   = "/{" + DATASOURCE_ID + "}";
    String ENTITY_KEY_ID_PATH   = "/{" + ENTITY_KEY_ID + "}";
    String ORGANIZATION_ID_PATH = "/{" + ORGANIZATION_ID + "}";
    String PARTICIPANT_ID_PATH  = "/{" + PARTICIPANT_ID + "}";
    String STUDY_ID_PATH        = "/{" + STUDY_ID + "}";

    /**
     * Enrolls a participant's mobile app-device instance in a study.
     * <p>
     * The legacy datasourceId is Chronicle's app-private device-instance ID. It is
     * not an Android hardware identifier and is only stable while app data is intact.
     *
     * @param organizationId Id of the organization to which study belongs
     * @param studyId        The id of the study.
     * @param participantId  The participant id associated with the app instance.
     * @param datasourceId   Chronicle app-instance ID.
     * @param datasource     Sanitized mobile device metadata.
     * @return The deduped server device UUID for this study/participant/app instance.
     */

    @POST( BASE + ORGANIZATION_ID_PATH + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH + ENROLL_PATH )
    UUID enroll(
            @Path( ORGANIZATION_ID ) UUID organizationId,
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId,
            @Path( DATASOURCE_ID ) String datasourceId,
            @Body Optional<SourceDevice> datasource );

    /**
     * Verify that daily push notifications are enabled for participant devices associated with a study
     *
     * @param organizationId - Id of organization to which study belongs
     * @param studyId        - study id
     * @return true if notifications are enabled for a given study
     */
    @GET( BASE + ORGANIZATION_ID_PATH + STUDY_ID_PATH + NOTIFICATIONS_PATH )
    Boolean isNotificationsEnabled(
            @Path( ORGANIZATION_ID ) UUID organizationId,
            @Path( STUDY_ID ) UUID studyId
    );

    /**
     * Query the enrollment status of a participant
     *
     * @param organizationId - Id of the organization to which study belongs
     * @param studyId        - studyId
     * @param participantId  - participantId
     * @return one of { ENROLLED, NOT_ENROLLED, UNKNOWN }
     */
    @GET( BASE + ORGANIZATION_ID_PATH + STUDY_ID_PATH + PARTICIPANT_ID_PATH + ENROLLMENT_STATUS_PATH )
    ParticipationStatus getParticipationStatus(
            @Path( ORGANIZATION_ID ) UUID organizationId,
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId
    );

    /**
     * Get all questionnaires for a given study
     *
     * @param organizationId - Id of the organization to which study belongs
     * @param studyId        - studyId
     * @return a mapping entityKeyId to entity details(name, description, cron etc)
     * or an empty Map if no questionnaires are found.
     */
    @GET( BASE + ORGANIZATION_ID_PATH + STUDY_ID_PATH + QUESTIONNAIRES_PATH )
    Map<UUID, Map<FullQualifiedName, Set<Object>>> getStudyQuestionnaires(
            @Path( ORGANIZATION_ID ) UUID organizationId,
            @Path( STUDY_ID ) UUID studyId
    );

    /**
     * Apps V2: Writes log data for specific studyId and participantId using a specific deviceId to a specific entitySetId.
     *
     * @param organizationId Id of the organization to which study belongs
     * @param studyId        The study id to associate the data with.
     * @param participantId  The participant id to associate the data with.
     * @param datasourceId   The device id logging the data.
     * @param data           The data / entities to write
     * @return The total number of items persisted by the server.
     */
    @POST( BASE + ORGANIZATION_ID_PATH + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH + UPLOAD_PATH )
    Integer upload(
            @Path( ORGANIZATION_ID ) UUID organizationId,
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId,
            @Path( DATASOURCE_ID ) String datasourceId,
            @Body List<SetMultimap<UUID, Object>> data );

    /**
     * Looks up property type ids for the requested property type FQNS (passed as Strings)
     *
     * @param propertyTypeFqns A set of property type FQNs (as Strings)
     * @return A map from property type FQNs to their corresponding ids
     */
    @POST( BASE + EDM_PATH )
    Map<FullQualifiedName, UUID> getPropertyTypeIds( @Body Set<FullQualifiedName> propertyTypeFqns );

    @GET( BASE + STATUS_PATH )
    Boolean isRunning();
}
