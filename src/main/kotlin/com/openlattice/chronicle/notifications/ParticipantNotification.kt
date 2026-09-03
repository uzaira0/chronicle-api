package com.openlattice.chronicle.notifications

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime
import java.util.*

/**
 * @author Todd Bergman <todd@openlattice.com>
 */

public data class ParticipantNotification(
    @field:NotBlank(message = "Participant ID is required")
    @field:Size(max = 255, message = "Participant ID exceeds maximum length")
    val participantId: String,

    @field:NotNull(message = "Notification type is required")
    val notificationType: NotificationType,

    @field:NotNull(message = "Delivery type is required")
    val deliveryType: EnumSet<DeliveryType>,

    @field:NotBlank(message = "Message is required")
    @field:Size(max = 2000, message = "Message exceeds maximum length")
    val message: String,

    val dateTime: OffsetDateTime = OffsetDateTime.now(),
)
