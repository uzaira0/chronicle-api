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
package com.openlattice.chronicle.authorization

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime
import java.util.*

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public data class AceValue @JvmOverloads constructor(
        @param:JsonProperty("permissions") val permissions: EnumSet<Permission>,
        @param:JsonProperty("securableObjectType") var securableObjectType: SecurableObjectType,
        @param:JsonProperty("expirationDate") var expirationDate: OffsetDateTime = OffsetDateTime.MAX
) : MutableSet<Permission> by permissions {
    @get:JsonIgnore
    override val size: Int
        get() = permissions.size

    @JsonIgnore
    override fun isEmpty(): Boolean = permissions.isEmpty()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AceValue

        if (permissions != other.permissions) return false
        if (securableObjectType != other.securableObjectType) return false
        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = permissions.hashCode()
        result = 31 * result + securableObjectType.hashCode()
        result = 31 * result + expirationDate.hashCode()
        return result
    }

}
