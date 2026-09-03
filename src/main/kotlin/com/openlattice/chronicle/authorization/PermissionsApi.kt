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
package com.openlattice.chronicle.authorization

import com.openlattice.chronicle.base.OK
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public interface PermissionsApi {
    public companion object {
        public const val SERVICE: String = "/chronicle"
        public const val CONTROLLER: String = "/v3/permissions"
        public const val BASE: String = SERVICE + CONTROLLER
        public const val BULK: String = "/bulk"
        public const val EXPLAIN: String = "/explain"
        public const val UPDATE: String = "/update"
    }


    /**
     * Adds, removes, or sets the ace for a particular acl key. Successful only if user is the owner of acl key.
     *
     *
     * If the action is ADD, all specified permissions will be merged into any existing sets of permissions for the given users.
     * If the action is SET, all specified permissions will replace any existing sets of permissions for the given users.
     * If the action is REMOVE, all specified permissions will be removed from any existing sets of permissions for the given users.
     *
     *
     * @param req The acl key, the principals, and the aces to set for that particular ace key.
     */
    @PATCH(BASE)
    public fun updateAcl(@Body req: AclData): OK

    /**
     * Adds, removes, or sets the ace for a particular set of acl keys. Successful only if user is the owner of all acl keys.
     *
     *
     * If the action is ADD, all specified permissions will be merged into any existing sets of permissions for the given users.
     * If the action is SET, all specified permissions will replace any existing sets of permissions for the given users.
     * If the action is REMOVE, all specified permissions will be removed from any existing sets of permissions for the given users.
     *
     *
     * @param req The acl key, the principals, and the aces to set for that particular ace key.
     * @return Void
     */
    @PATCH(BASE + UPDATE)
    public fun updateAcls(@Body req: List<AclData>): OK

    /**
     * Retrieves the acl for a particular acl key. Only return if user is the owner of acl key.
     *
     * @param aclKey The acl key.
     */
    @POST(BASE)
    public fun getAcl(@Body aclKey: AclKey): Acl

    /**
     * Retrieves the set of Acls for the given set of AclKeys, only if the user is OWNER of all AclKeys.
     *
     * @param aclKeys the set of AclKeys to operate on
     * @return the set of Acls corresponding to the given set of AclKeys
     */
    @POST(BASE + BULK)
    public fun getAcls(@Body aclKeys: Set<AclKey>): Set<Acl>

    /**
     * Retrieves the acl for a particular acl key, with explanation of where the permissions come from. Only return if
     * user is the owner of acl key.
     *
     * @param aclKey The acl key.
     * @return The aces for the requested acl key, together with the explanation.
     */
    @POST(BASE + EXPLAIN)
    public fun getAclExplanation(@Body aclKey: AclKey): Collection<AclExplanation>

}
