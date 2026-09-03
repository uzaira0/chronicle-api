/*
 * Copyright (C) 2024. Chronicle.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openlattice.chronicle.audit

/**
 * Enumeration of all auditable actions in the Chronicle system.
 * These are used for HIPAA compliance tracking and security monitoring.
 *
 * Categories:
 * - Authentication: LOGIN, LOGOUT, LOGIN_FAILED
 * - Data Access: VIEW, SEARCH, EXPORT, DOWNLOAD
 * - Data Modification: CREATE, UPDATE, DELETE
 * - Administrative: PERMISSION_CHANGE, SETTINGS_CHANGE
 * - Security: UNAUTHORIZED_ACCESS
 * - Mobile API: DATA_SUBMISSION
 */
public enum class AuditAction {
    // Authentication events
    LOGIN,
    LOGOUT,
    LOGIN_FAILED,
    TOKEN_REFRESH,
    SESSION_EXPIRED,

    // Data access events (read operations)
    VIEW,
    SEARCH,
    LIST,
    EXPORT,
    DOWNLOAD,

    // Data modification events (write operations)
    CREATE,
    UPDATE,
    DELETE,
    ARCHIVE,
    RESTORE,

    // Permission and administrative events
    PERMISSION_CHANGE,
    PERMISSION_GRANT,
    PERMISSION_REVOKE,
    SETTINGS_CHANGE,
    CONFIGURATION_CHANGE,

    // Security events
    UNAUTHORIZED_ACCESS,
    ACCESS_DENIED,
    INVALID_REQUEST,
    RATE_LIMITED,
    SUSPICIOUS_ACTIVITY,

    // Mobile API specific events
    DATA_SUBMISSION,
    DEVICE_ENROLLMENT,
    DEVICE_UNENROLLMENT,
    SENSOR_DATA_UPLOAD,
    USAGE_DATA_UPLOAD,
    BATTERY_TELEMETRY_UPLOAD,
    INTERACTION_EVENTS_UPLOAD,
    COLLECTION_ACKNOWLEDGMENT,

    // Study-specific events
    STUDY_CREATE,
    STUDY_UPDATE,
    STUDY_DELETE,
    PARTICIPANT_ENROLL,
    PARTICIPANT_WITHDRAW,
    PARTICIPANT_DATA_ACCESS,

    // Notification events
    NOTIFICATION_SENT,
    NOTIFICATION_FAILED,

    // Background job events
    JOB_CREATED,
    JOB_COMPLETED,
    JOB_FAILED,
    DATA_DELETION
}
