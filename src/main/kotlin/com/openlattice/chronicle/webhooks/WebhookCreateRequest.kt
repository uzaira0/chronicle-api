package com.openlattice.chronicle.webhooks

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

public data class WebhookCreateRequest(
    @field:NotBlank(message = "Webhook URL is required")
    @field:Pattern(regexp = "(?i)^https://.*", message = "Webhook URL must use HTTPS")
    @field:Size(max = 2048, message = "Webhook URL exceeds maximum length")
    val url: String,

    @field:NotBlank(message = "Webhook secret is required")
    @field:Size(min = 32, max = 255, message = "Webhook secret must be between 32 and 255 characters")
    val secret: String = "",

    @field:NotEmpty(message = "At least one event type is required")
    val eventTypes: Set<WebhookEventType> = emptySet(),

    @field:Size(max = 1000, message = "Description exceeds maximum length")
    val description: String = ""
)
