package com.openlattice.chronicle.organizations

import com.openlattice.chronicle.base.OK
import com.openlattice.chronicle.study.StudyApi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.*

public interface OrganizationMemberApi {
    public companion object {
        public const val ORGANIZATION_BASE: String = StudyApi.SERVICE + "/v3/organization"
        public const val ORGANIZATION_ID_PATH: String = "/{organizationId}"
        public const val MEMBERS_PATH: String = "/members"
        public const val QUOTAS_PATH: String = "/quotas"
        public const val USER_ID: String = "userId"
        public const val USER_ID_PATH: String = "/{$USER_ID}"
    }

    @POST(ORGANIZATION_BASE + ORGANIZATION_ID_PATH + MEMBERS_PATH)
    public fun addMember(
        @Path("organizationId") organizationId: UUID,
        @Body member: OrganizationMember
    ): OK

    @GET(ORGANIZATION_BASE + ORGANIZATION_ID_PATH + MEMBERS_PATH)
    public fun listMembers(@Path("organizationId") organizationId: UUID): List<OrganizationMember>

    @DELETE(ORGANIZATION_BASE + ORGANIZATION_ID_PATH + MEMBERS_PATH + USER_ID_PATH)
    public fun removeMember(
        @Path("organizationId") organizationId: UUID,
        @Path(USER_ID) userId: String
    ): OK

    @GET(ORGANIZATION_BASE + ORGANIZATION_ID_PATH + QUOTAS_PATH)
    public fun getQuotas(@Path("organizationId") organizationId: UUID): OrganizationQuotas

    @PUT(ORGANIZATION_BASE + ORGANIZATION_ID_PATH + QUOTAS_PATH)
    public fun updateQuotas(
        @Path("organizationId") organizationId: UUID,
        @Body quotas: OrganizationQuotas
    ): OrganizationQuotas
}
