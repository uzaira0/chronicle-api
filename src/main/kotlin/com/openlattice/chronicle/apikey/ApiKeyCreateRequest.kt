package com.openlattice.chronicle.apikey

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * Request to create a new API key.
 *
 * @property name Human-readable name for the key.
 * @property scope Permission scope for this key.
 * @property expiresInDays Number of days until the key expires. Default 90.
 */
public data class ApiKeyCreateRequest(
    @field:NotBlank(message = "API key name is required")
    @field:Size(max = 255, message = "API key name exceeds maximum length")
    val name: String = "",

    @field:NotNull(message = "API key scope is required")
    val scope: ApiKeyScope = ApiKeyScope.READ_ONLY,

    @field:Min(value = 1, message = "Expiration must be at least 1 day")
    @field:Max(value = 365, message = "Expiration must not exceed 365 days")
    val expiresInDays: Int = 90
)
