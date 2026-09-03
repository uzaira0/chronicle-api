package com.openlattice.chronicle.survey

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime
import java.util.*

/**
 * @author alfoncenzioka &lt;alfonce@openlattice.com&gt;
 */
public data class AppUsage(
    @field:NotBlank(message = "App package name is required")
    @field:Size(max = 500, message = "App package name exceeds maximum length")
    val appPackageName: String,

    @field:Size(max = 255, message = "App label exceeds maximum length")
    var appLabel: String?,

    @field:NotNull(message = "Timestamp is required")
    val timestamp: OffsetDateTime,

    val eventType: Int,

    @field:Size(max = 100, message = "Users list exceeds maximum count")
    val users: List<@Size(max = 255, message = "User value exceeds maximum length") String> = listOf(),

    @field:NotBlank(message = "Timezone is required")
    @field:Size(max = 100, message = "Timezone exceeds maximum length")
    val timezone: String,

    val uploadedAt: Optional<OffsetDateTime>
) {
    init {
        if (appLabel?.isBlank() == true) {
            appLabel = appPackageName
        }
    }
}
