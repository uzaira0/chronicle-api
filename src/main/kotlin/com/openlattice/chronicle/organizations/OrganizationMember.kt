package com.openlattice.chronicle.organizations

import java.time.OffsetDateTime
import java.util.*

public data class OrganizationMember(
    val organizationId: UUID,
    val userId: String,
    val role: OrganizationRole,
    val addedAt: OffsetDateTime? = null
)
