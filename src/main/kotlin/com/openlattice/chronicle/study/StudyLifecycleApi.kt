package com.openlattice.chronicle.study

import com.openlattice.chronicle.EditRevisionApi
import com.openlattice.chronicle.base.OK
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
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
    }

    /**
     * Archives a study. Archived studies are read-only for non-admin users. [revision] is the study ETag as
     * read; missing is 428, stale is 412.
     */
    @POST(BASE + StudyApi.STUDY_ID_PATH + ARCHIVE_PATH)
    public fun archiveStudy(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Header(EditRevisionApi.IF_MATCH) revision: String?,
    ): OK

    /**
     * Restores an archived study to active status.
     */
    @POST(BASE + StudyApi.STUDY_ID_PATH + UNARCHIVE_PATH)
    public fun unarchiveStudy(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Header(EditRevisionApi.IF_MATCH) revision: String?,
    ): OK

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
     * Requests the study's deletion: its data is blocked at once and stays until an owner runs the
     * erasure from the study's deletion list. Nothing is erased on a schedule.
     */
    @DELETE(BASE + StudyApi.STUDY_ID_PATH + SCHEDULE_DELETE_PATH)
    public fun scheduleStudyDeletion(@Path(StudyApi.STUDY_ID) studyId: UUID): OK

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
}
