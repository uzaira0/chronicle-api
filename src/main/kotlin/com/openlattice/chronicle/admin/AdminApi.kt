package com.openlattice.chronicle.admin

import com.openlattice.chronicle.authorization.Principal
import retrofit2.http.GET
import retrofit2.http.Path

public interface AdminApi {
    public companion object {
        // @formatter:off
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/admin"
        public const val BASE: String = SERVICE + CONTROLLER
        // @formatter:on

        public const val RELOAD_CACHE: String = "/reload/cache"
        public const val PRINCIPALS: String = "/principals"
        public const val EVENT_STORAGE: String = "/event-storage"

        public const val ID: String = "id"
        public const val ID_PATH: String = "/{$ID}"
        public const val NAME: String = "name"
        public const val NAME_PATH: String = "/{$NAME}"
    }

    @GET(BASE + EVENT_STORAGE)
    public fun moveToEventStorage()

    /**
     * Reload the all the in memory caches.
     */
    @GET(BASE + RELOAD_CACHE)
    public fun reloadCache()

    @GET(BASE + RELOAD_CACHE + NAME_PATH)
    public fun reloadCache(@Path(NAME) name: String)

    @GET(BASE + PRINCIPALS + ID_PATH)
    public fun getUserPrincipals(@Path(ID) principalId: String): Set<Principal>

    /**
     * Retrieves all the principals for the current user.
     * @return All the principals of the current user.
     */
    @GET(BASE + PRINCIPALS)
    public fun getCurrentUserPrincipals(): Set<Principal>
}
