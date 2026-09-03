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

import com.google.common.base.Preconditions
import java.util.UUID

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public data class AceKey(val aclKey: AclKey, val principal: Principal) {

    /**
     * Creates an ace key keeping the order of the key arguments
     *
     * @param key Set of object key to put in canonical order
     * @param principal The principal for this ACE
     */
    public constructor(principal: Principal, vararg key: UUID) : this(AclKey(key.asList()), principal)

    init {
        Preconditions.checkArgument(aclKey.size > 0, "At least one key must be provided.")
    }
}
