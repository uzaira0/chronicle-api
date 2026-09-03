package com.openlattice.chronicle.study

import com.openlattice.chronicle.base.OK
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.OffsetDateTime
import java.util.*

/**
 * API for study lifecycle management: archival, unarchival, cloning, and scheduled deletion.
 */
public interface StudyLifecycleApi {
    public companion object {
        public const val BASE: String = StudyApi.BASE

        public const val ARCHIVE_PATH: String = "/archive"
        public const val UNARCHIVE_PATH: String = "/unarchive"
        public const val CLONE_PATH: String = "/clone"
        public const val SCHEDULE_DELETE_PATH: String = "/schedule-delete"
        public const val LIFECYCLE_PATH: String = "/lifecycle"
        public const val DATA_SUMMARY_PATH: String = "/data-summary"
    }

    /**
     * Archives a study. Archived studies are read-only for non-admin users.
     */
    @POST(BASE + StudyApi.STUDY_ID_PATH + ARCHIVE_PATH)
    public fun archiveStudy(@Path(StudyApi.STUDY_ID) studyId: UUID): OK

    /**
     * Restores an archived study to active status.
     */
    @POST(BASE + StudyApi.STUDY_ID_PATH + UNARCHIVE_PATH)
    public fun unarchiveStudy(@Path(StudyApi.STUDY_ID) studyId: UUID): OK

    /**
     * Clones a study, optionally including participants and settings.
     * Returns the new study's UUID.
     */
    @POST(BASE + StudyApi.STUDY_ID_PATH + CLONE_PATH)
    public fun cloneStudy(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body request: StudyCloneRequest = StudyCloneRequest()
    ): UUID

    /**
     * Schedules a study for future deletion. The study will be permanently
     * deleted by a background job after the specified date.
     */
    @DELETE(BASE + StudyApi.STUDY_ID_PATH + SCHEDULE_DELETE_PATH)
    public fun scheduleStudyDeletion(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Query("deleteAfter") deleteAfter: OffsetDateTime
    ): OK

    /**
     * Cancels a scheduled deletion, returning the study to its previous lifecycle status.
     */
    @POST(BASE + StudyApi.STUDY_ID_PATH + SCHEDULE_DELETE_PATH)
    public fun cancelScheduledDeletion(@Path(StudyApi.STUDY_ID) studyId: UUID): OK

    /**
     * Gets the lifecycle status of a study.
     */
    @GET(BASE + StudyApi.STUDY_ID_PATH + LIFECYCLE_PATH)
    public fun getStudyLifecycleStatus(@Path(StudyApi.STUDY_ID) studyId: UUID): StudyLifecycleStatus

    /**
     * Gets a data summary for a study, including row counts per data table.
     * Useful before archival or deletion to understand scope of impact.
     */
    @GET(BASE + StudyApi.STUDY_ID_PATH + LIFECYCLE_PATH + DATA_SUMMARY_PATH)
    public fun getStudyDataSummary(@Path(StudyApi.STUDY_ID) studyId: UUID): StudyDataSummary
}
