package com.openlattice.chronicle;

import com.google.common.base.Optional;
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

public interface ChronicleStudyApi {

    String SERVICE    = "/chronicle";
    String CONTROLLER = "/study";
    String BASE       = SERVICE + CONTROLLER;

    String DATASOURCE_ID  = "datasourceId";
    String DATE           = "date";
    String ENTITY_KEY_ID  = "entityKeyId";
    String PARTICIPANT_ID = "participantId";
    String STUDY_ID       = "studyId";

    String APPS              = "/apps";
    String DATA_PATH         = "/data";
    String ENROLLMENT_STATUS = "/status";
    String NOTIFICATIONS     = "/notifications";
    String PARTICIPANT_PATH  = "/participant";
    String QUESTIONNAIRE     = "/questionnaire";
    String QUESTIONNAIRES    = "/questionnaires";
    String TIME_USE_DIARY    = "/time-use-diary";

    String DATASOURCE_ID_PATH  = "/{" + DATASOURCE_ID + "}";
    String ENTITY_KEY_ID_PATH  = "/{" + ENTITY_KEY_ID + "}";
    String PARTICIPANT_ID_PATH = "/{" + PARTICIPANT_ID + "}";
    String STUDY_ID_PATH       = "/{" + STUDY_ID + "}";

    /**
     * Enrolls a participant's mobile app-device instance in a study.
     * <P>
     * The legacy datasourceId is Chronicle's app-private device-instance ID. It is
     * not an Android hardware identifier and is only stable while app data is intact.
     *
     * @param studyId       The id of the study.
     * @param participantId The participant id associated with the app instance.
     * @param datasourceId  Chronicle app-instance ID.
     * @param datasource    Sanitized mobile device metadata.
     * @return The deduped server device UUID for this study/participant/app instance.
     */
    @POST( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH )
    UUID enrollSource(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId,
            @Path( DATASOURCE_ID ) String datasourceId,
            @Body Optional<SourceDevice> datasource );

    /**
     * Verifies that a participant in a study is associated with a specific app-device instance.
     *
     * @param studyId       - the study id
     * @param participantId - the participant id
     * @param datasourceId  - Chronicle app-instance ID
     * @return Whether the app instance is tied to this participant in this study
     */
    @GET( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH )
    Boolean isKnownDatasource(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId,
            @Path( DATASOURCE_ID ) String datasourceId );

    /**
     * Verify that daily push notifications are enabled for participant devices associated with a study
     *
     * @param studyId - study id
     * @return true if notifications are enabled for a given study
     */
    @GET( BASE + STUDY_ID_PATH + NOTIFICATIONS )
    Boolean isNotificationsEnabled(
            @Path( STUDY_ID ) UUID studyId
    );

    /**
     * Query the enrollment status of a participant
     *
     * @param studyId       - studyId
     * @param participantId - participantId
     * @return one of { ENROLLED, NOT_ENROLLED, UNKNOWN }
     */
    @GET( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH + ENROLLMENT_STATUS )
    ParticipationStatus getParticipationStatus(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId
    );

    /**
     * Get all questionnaires for a given study
     *
     * @param studyId - studyId
     * @return a mapping entityKeyId to entity details(name, description, cron etc)
     * or an empty Map if no questionnaires are found.
     */
    @GET( BASE + STUDY_ID_PATH + QUESTIONNAIRES )
    Map<UUID, Map<FullQualifiedName, Set<Object>>> getStudyQuestionnaires(
            @Path( STUDY_ID ) UUID studyId
    );
}
