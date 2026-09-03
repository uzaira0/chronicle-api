package com.openlattice.chronicle.webhooks

import java.time.OffsetDateTime
import java.util.*

public enum class WebhookDeliveryState {
    PENDING,
    IN_FLIGHT,
    SUCCEEDED,
    FAILED,
}

public data class WebhookDeliveryInfo(
    val deliveryId: UUID,
    val webhookId: UUID,
    val eventType: WebhookEventType,
    val status: Int,
    val attemptCount: Int,
    val createdAt: OffsetDateTime,
    val lastAttemptAt: OffsetDateTime? = null,
    val deliveryState: WebhookDeliveryState? = null,
    val outcomeCode: String? = null,
    val availableAt: OffsetDateTime? = null,
    val completedAt: OffsetDateTime? = null,
)
