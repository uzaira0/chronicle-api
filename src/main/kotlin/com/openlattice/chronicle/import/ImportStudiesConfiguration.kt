package com.openlattice.chronicle.import

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public data class ImportStudiesConfiguration(
    @field:NotBlank(message = "Data source name is required")
    @field:Size(max = 255, message = "Data source name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Data source name contains invalid characters")
    val dataSourceName: String,

    @field:NotBlank(message = "Candidates table name is required")
    @field:Size(max = 255, message = "Candidates table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Candidates table name contains invalid characters")
    val candidatesTable: String,

    @field:NotBlank(message = "Studies table name is required")
    @field:Size(max = 255, message = "Studies table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Studies table name contains invalid characters")
    val studiesTable: String,

    @field:NotBlank(message = "Study settings table name is required")
    @field:Size(max = 255, message = "Study settings table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Study settings table name contains invalid characters")
    val studySettingsTable: String,

    @field:Size(max = 255, message = "Time use diary table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Time use diary table name contains invalid characters")
    val timeUseDiaryTable: String? = "",

    @field:Size(max = 255, message = "Participant stats table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Participant stats table name contains invalid characters")
    val participantStatsTable: String? = "",

    @field:Size(max = 255, message = "App usage survey table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "App usage survey table name contains invalid characters")
    val appUsageSurveyTable: String? = "",

    @field:Size(max = 255, message = "System apps table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "System apps table name contains invalid characters")
    val systemAppsTable: String? = "",

    @field:Size(max = 255, message = "Users table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Users table name contains invalid characters")
    val usersTable: String? = "",

    @field:Size(max = 255, message = "Legacy users table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Legacy users table name contains invalid characters")
    val legacyUsersTable: String? = "",

    @field:Size(max = 255, message = "Time use diary summarized table name exceeds maximum length")
    @field:Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Time use diary summarized table name contains invalid characters")
    val timeUseDiarySummarizedTable: String? = ""
)
