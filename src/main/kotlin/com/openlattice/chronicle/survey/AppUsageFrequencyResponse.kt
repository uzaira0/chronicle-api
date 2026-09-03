package com.openlattice.chronicle.survey

import com.openlattice.chronicle.settings.AppUsageFrequency

/**
 * Participant-readable response carrying the study's configured app-usage survey frequency, so the
 * web survey can render the correct variant (DAILY app-by-app vs HOURLY time-bucketed). Mirrors
 * upstream methodic-labs, which selects the survey from this study setting. Defaults to DAILY.
 *
 * Returned by the unauthenticated `GET /chronicle/v3/survey/{studyId}/app-usage-frequency`; a flat
 * wrapper (rather than a bare enum) keeps the OpenAPI schema and the web type clean.
 */
public data class AppUsageFrequencyResponse(
    val appUsageFrequency: AppUsageFrequency,
)
