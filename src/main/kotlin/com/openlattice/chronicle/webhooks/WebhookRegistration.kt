package com.openlattice.chronicle.webhooks

import java.time.OffsetDateTime
import java.util.*

public data class WebhookRegistration(
    val webhookId: UUID = UUID.randomUUID(),
    val studyId: UUID = UUID(0, 0),
    val url: String = "",
    val secret: String = "",
    val eventTypes: Set<WebhookEventType> = emptySet(),
    val enabled: Boolean = true,
    val description: String = "",
    val createdAt: OffsetDateTime? = null
)
