package com.openlattice.chronicle.pipeline

import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

public interface PipelineApi {

    public companion object {
        public const val PIPELINE_PATH: String = "/pipeline"
        public const val RUN_ID: String = "runId"
        public const val RUN_ID_PATH: String = "/{$RUN_ID}"
        public const val TRIGGER_PATH: String = "/trigger"
        public const val RUNS_PATH: String = "/runs"
    }

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + PIPELINE_PATH + TRIGGER_PATH)
    public fun triggerPipeline(@Path(StudyApi.STUDY_ID) studyId: UUID): PipelineRunInfo

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + PIPELINE_PATH + RUNS_PATH)
    public fun listPipelineRuns(@Path(StudyApi.STUDY_ID) studyId: UUID): List<PipelineRunInfo>

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + PIPELINE_PATH + RUNS_PATH + RUN_ID_PATH)
    public fun getPipelineRun(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Path(RUN_ID) runId: UUID,
    ): PipelineRunInfo
}
