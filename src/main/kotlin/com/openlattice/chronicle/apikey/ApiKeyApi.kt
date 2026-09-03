package com.openlattice.chronicle.apikey

import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

public interface ApiKeyApi {
    public companion object {
        public const val API_KEYS_PATH: String = "/api-keys"
        public const val KEY_ID: String = "keyId"
        public const val KEY_ID_PATH: String = "/{$KEY_ID}"
        public const val ROTATE_PATH: String = "/rotate"
    }

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + API_KEYS_PATH)
    public fun createApiKey(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body request: ApiKeyCreateRequest
    ): ApiKeyCreateResponse

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + API_KEYS_PATH)
    public fun listApiKeys(@Path(StudyApi.STUDY_ID) studyId: UUID): List<ApiKeyInfo>

    @DELETE(StudyApi.BASE + StudyApi.STUDY_ID_PATH + API_KEYS_PATH + KEY_ID_PATH)
    public fun revokeApiKey(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(KEY_ID) keyId: UUID
    ): OK

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + API_KEYS_PATH + KEY_ID_PATH + ROTATE_PATH)
    public fun rotateApiKey(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(KEY_ID) keyId: UUID
    ): ApiKeyCreateResponse
}
