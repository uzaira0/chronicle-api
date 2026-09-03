package com.openlattice.chronicle.apikey

import java.time.OffsetDateTime
import java.util.*

/**
 * API key metadata returned by list/get endpoints. Never includes the raw key.
 */
public data class ApiKeyInfo(
    val keyId: UUID,
    val studyId: UUID,
    val prefix: String,
    val name: String,
    val scope: ApiKeyScope,
    val createdAt: OffsetDateTime,
    val expiresAt: OffsetDateTime,
    val lastUsedAt: OffsetDateTime? = null,
    val usageCount: Long = 0,
    /** Set on mobile keys; null on admin keys. Mobile keys are bound to a single participant. */
    val participantId: String? = null,
    /** Set on mobile keys; null on admin keys. Mobile keys are bound to a single device. */
    val deviceId: UUID? = null
)
