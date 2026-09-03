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
import java.time.OffsetDateTime

public data class Ace constructor(
        @field:Valid
        @field:NotNull(message = "Principal is required")
        @param:JsonProperty(JsonFields.PRINCIPAL) val principal: Principal,

        @field:NotNull(message = "Permissions are required")
        @param:JsonProperty(JsonFields.PERMISSIONS) val permissions: Set<Permission>,

        @param:JsonProperty(JsonFields.EXPIRATION) val expirationDate: OffsetDateTime = OffsetDateTime.MAX
) {

    @Transient
    private var hashValue = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ace

        if (principal != other.principal) return false
        if (permissions != other.permissions) return false
        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        if( hashValue == 0 ) {
            var result = principal.hashCode()
            result = 31 * result + permissions.hashCode()
            result = 31 * result + expirationDate.hashCode()
            hashValue =  result
        }
        return hashValue
    }
}
