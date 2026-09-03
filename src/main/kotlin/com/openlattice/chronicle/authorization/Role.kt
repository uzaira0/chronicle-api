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
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Preconditions
import com.openlattice.chronicle.util.JsonFields
import java.util.*

public class Role constructor(
    aclKey: AclKey,
    public val organizationId: UUID,
    principal: Principal,
    title: String,
    description: Optional<String>
) : SecurablePrincipal(aclKey, principal, title, description) {

    init {
        Preconditions.checkArgument(principal.type == PrincipalType.ROLE)
    }

    @JsonCreator
    public constructor(
        @JsonProperty(JsonFields.ID_FIELD) id: Optional<UUID>,
        @JsonProperty(JsonFields.ORGANIZATION_ID) organizationId: UUID,
        @JsonProperty(JsonFields.PRINCIPAL) principal: Principal,
        @JsonProperty(JsonFields.TITLE_FIELD) title: String,
        @JsonProperty(JsonFields.DESCRIPTION_FIELD) description: Optional<String>
    ) : this(AclKey(organizationId, id.orElseGet { UUID.randomUUID() }), principal, title, description)
    public constructor(
        aclKey: AclKey,
        principal: Principal,
        title: String,
        description: Optional<String>
    ) : this(aclKey,aclKey.first(), principal, title, description)

    @JsonIgnore
    override val category: SecurableObjectType = SecurableObjectType.Role

    override fun toString(): String {
        return "RoleKey { " +
                "organizationId=" + organizationId.toString() +
                ", roleId=" + id.toString() +
                ", title=" + title +
                " }"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Role

        if (organizationId != other.organizationId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + organizationId.hashCode()
        return result
    }
}
