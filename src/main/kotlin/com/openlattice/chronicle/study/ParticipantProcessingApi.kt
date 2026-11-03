package com.openlattice.chronicle.study

import com.openlattice.chronicle.EditRevisionApi
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

public data class ParticipantProcessingBlockRequest(
    @field:NotBlank
    @field:Size(max = 4000, message = "Reason exceeds maximum length")
    val reason: String,
)

public interface ParticipantProcessingApi {
    public companion object {
        public const val PROCESSING_BLOCKED_REASON: String = "Participant processing is blocked. Contact your study team."
        public const val PATH: String = StudyApi.STUDY_ID_PATH + StudyApi.PARTICIPANT_PATH +
            StudyApi.PARTICIPANT_ID_PATH + "/processing-block"
    }

    @POST(StudyApi.BASE + PATH)
    public fun blockParticipantProcessing(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(StudyApi.PARTICIPANT_ID) participantId: String,
        @Body request: ParticipantProcessingBlockRequest,
        @Header(EditRevisionApi.IF_MATCH) revision: String?,
    )

    @DELETE(StudyApi.BASE + PATH)
    public fun unblockParticipantProcessing(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(StudyApi.PARTICIPANT_ID) participantId: String,
        @Header(EditRevisionApi.IF_MATCH) revision: String?,
    )
}
