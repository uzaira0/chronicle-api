/*
 * Copyright (C) 2018. OpenLattice, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You can contact the owner of the copyright at support@openlattice.com
 */
package com.openlattice.chronicle.users

import com.openlattice.chronicle.authorization.AclKey
import com.openlattice.chronicle.authorization.Principal
import com.openlattice.chronicle.authorization.Role
import com.openlattice.chronicle.authorization.SecurablePrincipal
import com.openlattice.chronicle.base.OK
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// reason: public Retrofit API surface — the full principal endpoint contract; splitting would break clients
@Suppress("TooManyFunctions")
public interface PrincipalApi {

    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/principal"
        public const val BASE: String = SERVICE + CONTROLLER

        public const val CURRENT: String = "/current"
        public const val ROLES: String = "/roles"
        public const val SEARCH: String = "/search"
        public const val SYNC: String = "/sync"
        public const val UPDATE: String = "/update"
        public const val USERS: String = "/users"
        public const val USER_ID: String = "userId"
        public const val USER_ID_PATH: String = "/{$USER_ID}"
    }

    @POST(BASE)
    public fun getSecurablePrincipal(@Body principal: Principal): SecurablePrincipal

    @GET(BASE + USERS)
    public fun getAllUsers(): Map<String, ChronicleUserProfile>

    @GET(BASE + ROLES + CURRENT)
    public fun getCurrentRoles(): Set<SecurablePrincipal>

    @GET(BASE + ROLES)
    public fun getAvailableRoles(): Map<AclKey, Role>

    @GET(BASE + USERS + USER_ID_PATH)
    public fun getUser(@Path(USER_ID) userId: String): ChronicleUserProfile

    @POST(BASE + USERS)
    public fun getUsers(@Body userIds: Set<String>): Map<String, ChronicleUserProfile>

    @POST(BASE + USERS + SEARCH)
    public fun searchUsers(@Body fields: UserSearchFields): Map<String, ChronicleUserProfile>

    /**
     * Activates a user in the OpenLattice system. This call must be made once before a user will be available for use
     * in authorization policies.
     *
     * @return Nothing
     */
    @GET(BASE + SYNC)
    public fun syncCallingUser(): OK

    @POST(BASE + UPDATE)
    public fun addPrincipalToPrincipal(@Body directedAclKeys: DirectedAclKeys): OK

    @DELETE(BASE + UPDATE)
    public fun removePrincipalFromPrincipal(@Body directedAclKeys: DirectedAclKeys): OK

    @DELETE(BASE + USERS + USER_ID_PATH)
    public fun deleteUserAccount(@Path(USER_ID) userId: String): OK
}
