package com.openlattice.chronicle.timeusediary

/**
 * Flat, participant-readable projection of [TimeUseDiarySettings] for the web Time Use Diary form.
 *
 * Returned by the unauthenticated participant endpoint `GET /chronicle/v3/time-use-diary/{studyId}/settings`,
 * so it deliberately drops the `@JsonTypeInfo`/`@class` discriminator carried by the internal
 * [TimeUseDiarySettings] (no need to leak the fully-qualified class name on an anonymous route) and
 * exposes only the non-sensitive variant flags the diary UI needs to render the study's configured
 * instrument (OSU/Sherbrooke question set, clock format, locale) instead of relying on URL params.
 */
public data class TimeUseDiarySettingsResponse(
    val enableChangesForOhioStateUniversity: Boolean,
    val enableChangesForSherbrookeUniversity: Boolean,
    val clockFormat: Int,
    val clockFormatLocked: Boolean,
    val language: String,
) {
    public companion object {
        public fun from(settings: TimeUseDiarySettings): TimeUseDiarySettingsResponse =
            TimeUseDiarySettingsResponse(
                enableChangesForOhioStateUniversity = settings.enableChangesForOhioStateUniversity,
                enableChangesForSherbrookeUniversity = settings.enableChangesForSherbrookeUniversity,
                clockFormat = settings.clockFormat,
                clockFormatLocked = settings.clockFormatLocked,
                language = settings.language,
            )
    }
}
