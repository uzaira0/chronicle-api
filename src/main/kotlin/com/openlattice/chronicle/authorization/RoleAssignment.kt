package com.openlattice.chronicle.authorization

import java.time.OffsetDateTime
import java.util.UUID

public enum class ScopeType {
    STUDY,
    ORGANIZATION,
}

public data class RoleAssignment(
    val principalId: String,
    val principalType: PrincipalType = PrincipalType.USER,
    val scopeType: ScopeType,
    val scopeId: UUID,
    val role: ChronicleStudyRole,
    /**
     * Actor metadata is available only when it comes from an authoritative
     * assignment-history source. ACL-derived role views therefore return null.
     */
    val assignedBy: String? = null,
    /**
     * Assignment time is unavailable from the ACL itself and must not be
     * synthesized from response time or a sentinel value.
     */
    val assignedAt: OffsetDateTime? = null,
)
