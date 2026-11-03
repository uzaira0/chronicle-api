package com.openlattice.chronicle.survey

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.OffsetDateTime

/** Every ended survey day of a participant, oldest first, in the study time zone. */
public data class AppUsageSurveyDays(
    val timeZone: String,
    /** The server's current day in the study time zone; it has not ended, so it is not answerable yet. */
    val today: LocalDate,
    val days: List<AppUsageSurveyDay>,
)

public data class AppUsageSurveyDay(
    val date: LocalDate,
    val state: AppUsageDayState,
)

/** One ended survey day: the apps used for at least the study threshold, each split into its answer blocks. */
public data class AppUsageDaySurvey(
    val date: LocalDate,
    val timeZone: String,
    val state: AppUsageDayState,
    val apps: List<AppUsageDayApp>,
)

public data class AppUsageDayApp(
    val appPackageName: String,
    val appLabel: String,
    val blocks: List<AppUsageBlock>,
)

/** A DAILY survey has one block per app and day; an HOURLY survey has one per hour with use. */
public data class AppUsageBlock(
    val start: OffsetDateTime,
    val end: OffsetDateTime,
    /** Stored answer; null while the block is unanswered. */
    val users: List<String>? = null,
)

/** Who used one app in one block of the request's survey day. */
public data class AppUsageAnswer(
    @field:NotBlank(message = "App package name is required")
    @field:Size(max = 500, message = "App package name exceeds maximum length")
    val appPackageName: String,

    @field:NotNull(message = "Block start is required")
    val blockStart: OffsetDateTime,

    @field:Size(max = 100, message = "Users list exceeds maximum count")
    val users: List<@Size(max = 255, message = "User value exceeds maximum length") String>,
)
