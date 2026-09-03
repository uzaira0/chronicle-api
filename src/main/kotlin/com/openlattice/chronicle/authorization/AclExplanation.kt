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

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.openlattice.chronicle.util.JsonFields

public data class AclExplanation @JsonCreator constructor(
        @param:JsonProperty(JsonFields.PRINCIPAL) val principal: Principal,
        @param:JsonProperty(JsonFields.PRINCIPAL_PATHS) val paths: List<List<Principal>>
) {

    @Transient
    private var h: Int = 0

    override fun hashCode(): Int {
        if (h == 0) {
            val result = principal.hashCode()
            h = 31 * result + paths.hashCode()
        }
        return h
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AclExplanation

        if (principal != other.principal) return false
        if (paths != other.paths) return false

        return true
    }


}
