package com.openlattice.chronicle.study

import java.util.UUID

/**
 * Response returned by mobile enrollment endpoints.
 *
 * On servers that support per-device API keys (the new BCM server), [apiKey]
 * is populated with a freshly issued key bound to (studyId, participantId,
 * chronicleId). The client persists it and sends it as `X-Api-Key` for all
 * subsequent uploads to that server.
 *
 * On servers that don't issue per-device keys (the legacy upstream backup
 * server), [apiKey] is null and the client falls back to the legacy
 * `X-Chronicle-Device-Id` header for uploads.
 *
 * The client deserializer accepts either this object shape or a bare UUID
 * string (legacy v3/v4 response) for protocol compatibility.
 */
public data class EnrollmentResponse(
    val chronicleId: UUID,
    val enrollmentId: UUID? = null,
    val apiKey: String? = null
)
