package com.openlattice.chronicle.organizations

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public interface OrganizationApi {
    public companion object {
        // @formatter:off
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/organization"
        public const val BASE: String = SERVICE + CONTROLLER
        // @formatter:on

        public const val ORGANIZATION_ID: String = "organizationId"
        public const val ORGANIZATION_ID_PATH: String = "/{$ORGANIZATION_ID}"
        public const val SETTINGS_PATH: String = "/settings"
        public const val DATA_COLLECTION_PATH: String = "/data-collection"
        public const val APP_COMPONENT: String = "appComponent"
        public const val APP_COMPONENT_PATH: String = "/app-component"
        public const val APP_COMPONENT_PARAM_PATH: String = "/{$APP_COMPONENT}"
    }

    @POST(BASE)
    public fun createOrganization(@Body organization: Organization): UUID

    @GET(BASE + ORGANIZATION_ID_PATH)
    public fun getOrganization(@Path(ORGANIZATION_ID) organizationId: UUID): Organization

    @GET(BASE)
    public fun getOrganizations(): Iterable<Organization>

    

    

    

    

    

    
}
