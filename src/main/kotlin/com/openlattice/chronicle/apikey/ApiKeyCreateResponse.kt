package com.openlattice.chronicle.apikey

import java.util.*

/**
 * Response returned when creating an API key. Includes the raw key which is shown ONCE.
 */
public data class ApiKeyCreateResponse(
    val keyId: UUID,
    val rawKey: String,
    val info: ApiKeyInfo
)
