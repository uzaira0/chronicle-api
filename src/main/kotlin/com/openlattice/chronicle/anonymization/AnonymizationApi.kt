package com.openlattice.chronicle.anonymization

import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.*

public interface AnonymizationApi {
    public companion object {
        public const val ANONYMIZATION_PATH: String = "/anonymization"
    }

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + ANONYMIZATION_PATH)
    public fun getAnonymizationConfig(@Path(StudyApi.STUDY_ID) studyId: UUID): AnonymizationConfig

    @PUT(StudyApi.BASE + StudyApi.STUDY_ID_PATH + ANONYMIZATION_PATH)
    public fun updateAnonymizationConfig(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body config: AnonymizationConfig
    ): AnonymizationConfig
}
