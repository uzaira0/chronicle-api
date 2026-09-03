package com.openlattice.chronicle.users

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.openlattice.chronicle.authorization.AclKey
import com.openlattice.chronicle.util.JsonFields.SRC
import com.openlattice.chronicle.util.JsonFields.TARGET
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import java.util.*

public data class DirectedAclKeys @JsonCreator constructor(
    @field:Valid
    @field:NotNull(message = "Target ACL key is required")
    @param:JsonProperty(TARGET) public val target: AclKey,

    @field:Valid
    @field:NotNull(message = "Source ACL key is required")
    @param:JsonProperty(SRC) public val source: AclKey
)
