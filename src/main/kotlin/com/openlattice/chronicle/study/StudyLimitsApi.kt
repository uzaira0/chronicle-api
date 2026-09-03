package com.openlattice.chronicle.study

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.*

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public interface StudyLimitsApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/limits"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val STUDY_ID: String = "studyId"
        public const val STUDY: String = "/study"

        public const val STUDY_ID_PATH: String = "/{$STUDY_ID}"

    }

    @PUT(BASE + STUDY + STUDY_ID_PATH)
    public fun setStudyLimits(@Path(STUDY_ID) studyId: UUID, @Body studyLimits: StudyLimits)

    @GET(BASE + STUDY + STUDY_ID_PATH)
    public fun getStudyLimits(@Path(STUDY_ID) studyId: UUID): StudyLimits
}
