package com.openlattice.chronicle.export

import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

public interface ExportApi {
    public companion object {
        public const val EXPORT_PATH: String = "/export"
        public const val ASYNC_PATH: String = "/async"
        public const val DOWNLOAD_PATH: String = "/download"
        public const val EXPORT_ID: String = "exportId"
        public const val EXPORT_ID_PATH: String = "/{$EXPORT_ID}"
    }

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + EXPORT_PATH + ASYNC_PATH)
    public fun createAsyncExport(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body request: ExportRequest
    ): ExportJobInfo

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + EXPORT_PATH + EXPORT_ID_PATH)
    public fun getExportStatus(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(EXPORT_ID) exportId: UUID
    ): ExportJobInfo

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + EXPORT_PATH)
    public fun listExports(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
    ): List<ExportJobInfo>

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + EXPORT_PATH + EXPORT_ID_PATH + DOWNLOAD_PATH)
    public fun downloadExport(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(EXPORT_ID) exportId: UUID
    ): Unit
}
