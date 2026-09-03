package com.openlattice.chronicle.organizations

import com.openlattice.chronicle.settings.AppComponent
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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
        public const val SEARCH_PATH: String = "/search"
    }

    @POST(BASE)
    public fun createOrganization(@Body organization: Organization): UUID

    @GET(BASE + ORGANIZATION_ID_PATH)
    public fun getOrganization(@Path(ORGANIZATION_ID) organizationId: UUID): Organization

    @GET(BASE)
    public fun getOrganizations(): Iterable<Organization>

    @GET(BASE + SEARCH_PATH)
    public fun searchOrganizations(): Collection<Organization>

    @GET(BASE + ORGANIZATION_ID_PATH + SETTINGS_PATH)
    public fun getOrganizationSettings(@Path(ORGANIZATION_ID) organizationId: UUID): OrganizationSettings

    @GET(BASE + ORGANIZATION_ID_PATH + DATA_COLLECTION_PATH)
    public fun getChronicleDataCollectionSettings(@Path(ORGANIZATION_ID) organizationId: UUID): ChronicleDataCollectionSettings

    @GET(BASE + ORGANIZATION_ID_PATH + APP_COMPONENT_PATH + APP_COMPONENT_PARAM_PATH)
    public fun getAppComponentSettings(
        @Path(ORGANIZATION_ID) organizationId: UUID,
        @Path(APP_COMPONENT) appComponent: AppComponent
    ): Map<String, Any>

    @PUT(BASE + ORGANIZATION_ID_PATH + SETTINGS_PATH)
    public fun setOrganizationSettings(
        @Path(ORGANIZATION_ID) organizationId: UUID,
        @Body orgSettings: OrganizationSettings
    )

    @PUT(BASE + ORGANIZATION_ID_PATH + DATA_COLLECTION_PATH)
    public fun setChronicleDataCollectionSettings(
        @Path(ORGANIZATION_ID) organizationId: UUID,
        @Body dataCollectionSettings: ChronicleDataCollectionSettings
    )

    @PUT(BASE + ORGANIZATION_ID_PATH + APP_COMPONENT_PATH + APP_COMPONENT_PARAM_PATH)
    public fun setAppComponentSettings(
        @Path(ORGANIZATION_ID) organizationId: UUID,
        @Path(APP_COMPONENT) appComponent: AppComponent,
        @Body settings: Map<String, Any>
    )
}
