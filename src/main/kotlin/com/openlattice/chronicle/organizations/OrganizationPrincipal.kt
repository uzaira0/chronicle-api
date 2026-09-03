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
/*
 * Copyright (C) 2017. OpenLattice, Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * You can contact the owner of the copyright at support@openlattice.com
 *
 */
package com.openlattice.chronicle.organizations

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.openlattice.chronicle.authorization.AclKey
import com.openlattice.chronicle.authorization.Principal
import com.openlattice.chronicle.authorization.SecurableObjectType
import com.openlattice.chronicle.authorization.SecurablePrincipal
import com.openlattice.chronicle.util.JsonFields.DESCRIPTION_FIELD
import com.openlattice.chronicle.util.JsonFields.ID_FIELD
import com.openlattice.chronicle.util.JsonFields.PRINCIPAL
import com.openlattice.chronicle.util.JsonFields.TITLE_FIELD
import java.util.*

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public class OrganizationPrincipal : SecurablePrincipal {
    @JsonCreator
    public constructor(
            @JsonProperty(ID_FIELD) id: Optional<UUID>,
            @JsonProperty(PRINCIPAL) principal: Principal,
            @JsonProperty(TITLE_FIELD) title: String,
            @JsonProperty(DESCRIPTION_FIELD) description: Optional<String>
    ) : super(id, principal, title, description)

    public constructor(
            aclKey: AclKey,
            principal: Principal,
            title: String,
            description: Optional<String>
    ) : super(aclKey, principal, title, description)

    override val category: SecurableObjectType = SecurableObjectType.Organization
}
