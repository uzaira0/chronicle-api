package com.openlattice.chronicle;

import com.google.common.collect.SetMultimap;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ChronicleApi {

    String SERVICE    = "/chronicle";
    String CONTROLLER = "/data";
    String BASE       = SERVICE + CONTROLLER;

    String STUDY_ID        = "studyId";
    String PARTICIPANT_ID  = "participantId";
    String DATASOURCE_ID   = "datasourceId";
    String ENTITY_SET_ID   = "entitySetId";

    String STUDY_ID_PATH        = "/{" + STUDY_ID + "}";
    String PARTICIPANT_ID_PATH  = "/{" + PARTICIPANT_ID + "}";
    String DATASOURCE_ID_PATH   = "/{" + DATASOURCE_ID + "}";
    String ENTITY_SET_ID_PATH   = "/{" + ENTITY_SET_ID + "}";

    String EDM_PATH    = "/edm";
    String STATUS_PATH = "/status";

    /**
     * Writes log data for specific studyId and participantId using a specific deviceId to a specific entitySetId.
     *
     * @param studyId The study id to associate the data with.
     * @param participantId The participant id to associate the data with.
     * @param deviceId The device id logging the data.
     * @param data The data / entities to write
     * @return The total number of items persisted by the server.
     *
     */
    @POST( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH )
    Integer upload(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) String participantId,
            @Path( DATASOURCE_ID ) String deviceId,
            @Body List<SetMultimap<UUID, Object>> data );

    /**
     * Looks up property type ids for the requested property type FQNS (passed as Strings)
     *
     * @param propertyTypeFqns A set of property type FQNs (as Strings)
     * @return A map from property type FQNs to their corresponding ids
     *
     */
    @POST( BASE + EDM_PATH )
    Map<String, UUID> getPropertyTypeIds( @Body Set<String> propertyTypeFqns );

    @GET( BASE + STATUS_PATH )
    Boolean isRunning();
}
