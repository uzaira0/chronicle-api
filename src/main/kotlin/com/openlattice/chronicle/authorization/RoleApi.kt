package com.openlattice.chronicle.authorization

import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

public interface RoleApi {

    public companion object {
        public const val ROLES_PATH: String = "/roles"
    }

    @POST(StudyApi.BASE + StudyApi.STUDY_ID_PATH + ROLES_PATH)
    public fun assignRole(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body assignment: RoleAssignment,
    ): Unit

    @DELETE(StudyApi.BASE + StudyApi.STUDY_ID_PATH + ROLES_PATH)
    public fun revokeRole(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
        @Body assignment: RoleAssignment,
    ): Unit

    @GET(StudyApi.BASE + StudyApi.STUDY_ID_PATH + ROLES_PATH)
    public fun listRoleAssignments(
        @Path(StudyApi.STUDY_ID) studyId: UUID,
    ): List<RoleAssignment>
}
