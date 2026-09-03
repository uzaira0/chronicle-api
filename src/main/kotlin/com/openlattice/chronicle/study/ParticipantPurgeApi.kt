package com.openlattice.chronicle.study

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

public interface ParticipantPurgeApi {

    public companion object {
        public const val PURGE_PATH: String = "/purge"
        public const val PREVIEW_PATH: String = "/preview"
    }

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + StudyApi.PARTICIPANTS_PATH + StudyApi.PARTICIPANT_ID_PATH + PURGE_PATH + PREVIEW_PATH)
    public fun previewParticipantPurge(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(StudyApi.PARTICIPANT_ID) participantId: String,
    ): ParticipantDataPurgeSummary

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + StudyApi.PARTICIPANTS_PATH + PURGE_PATH)
    public fun executeParticipantPurge(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body request: ParticipantPurgeRequest,
    ): Iterable<UUID>
}
