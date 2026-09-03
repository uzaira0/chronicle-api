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

import com.fasterxml.jackson.annotation.JsonProperty
import com.openlattice.chronicle.util.JsonFields
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

public data class Acl constructor(
        @field:Valid
        @field:NotNull(message = "ACL key is required")
        @param:JsonProperty(JsonFields.ACL_OBJECT_PATH) val aclKey: AclKey,

        @field:Valid
        @field:NotNull(message = "ACEs are required")
        @param:JsonProperty(JsonFields.ACES) val aces: Iterable<Ace>
) {

    @Transient
    private var h = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Acl

        if (aclKey != other.aclKey) return false
        if (aces != other.aces) return false

        return true
    }

    override fun hashCode(): Int {
        //Should be fine as long as Aces isn't a mutable iterable. Should really consider an immutable collection.
        if (h == 0) {
            var result = aclKey.hashCode()
            result = 31 * result + aces.hashCode()
            h = result
        }

        return h
    }

}
