package com.openlattice.chronicle.authorization

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public interface AuthorizationsApi {
    public companion object {
        /*
        * These determine the service routing for the LB
        */
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/authorizations"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val OBJECT_TYPE: String = "objectType"
        public const val PERMISSION: String = "permission"
        public const val PAGING_TOKEN: String = "pagingToken"
    }

    @POST(BASE)
    public fun checkAuthorizations(@Body queries: Set<AccessCheck>): Iterable<Authorization>

    /**
     * Returns paged results for all authorized objects of specified objectType, that the current user has specified permission for.
     * @param objectType Required field. Specifying the Securable Object Type that user wants to search for.
     * @param permission Required field. Specifying the permission the user must have for the accessible objects.
     * @param pagingToken Unrequired field. One may use the paging token from previous search result to get to the next page of results.
     * @return
     */
    @GET(BASE)
    public fun getAccessibleObjects(
            @Query(OBJECT_TYPE) objectType: SecurableObjectType,
            @Query(PERMISSION) permission: Permission,
            @Query(PAGING_TOKEN) pagingToken: String
    ): AuthorizedObjectsSearchResult
}
