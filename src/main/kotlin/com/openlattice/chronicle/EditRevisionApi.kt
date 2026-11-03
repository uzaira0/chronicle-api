package com.openlattice.chronicle

import com.openlattice.chronicle.study.StudyApi
import com.openlattice.chronicle.generated.ChronicleHeaders
import com.openlattice.chronicle.survey.SurveyApi
import retrofit2.Call
import retrofit2.http.HEAD
import retrofit2.http.Path
import java.util.UUID

/** Native Retrofit calls expose the strong ETag required by conditional edits. */
public interface EditRevisionApi {
    @HEAD(SurveyApi.BASE + SurveyApi.STUDY_ID_PATH + SurveyApi.QUESTIONNAIRE_PATH + SurveyApi.QUESTIONNAIRE_ID_PATH)
    public fun questionnaire(
        @Path(SurveyApi.STUDY_ID) studyId: UUID,
        @Path(SurveyApi.QUESTIONNAIRE_ID) questionnaireId: UUID,
    ): Call<Void>

    @HEAD(StudyApi.BASE + StudyApi.STUDY_ID_PATH + StudyApi.PARTICIPANT_PATH + StudyApi.PARTICIPANT_ID_PATH + StudyApi.ANNOTATIONS_PATH)
    public fun participantAnnotations(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(StudyApi.PARTICIPANT_ID) participantId: String,
    ): Call<Void>

    public companion object {
        public const val IF_MATCH: String = ChronicleHeaders.EDIT_PRECONDITION_HEADER
        public const val ETAG: String = ChronicleHeaders.EDIT_REVISION_HEADER
    }
}
