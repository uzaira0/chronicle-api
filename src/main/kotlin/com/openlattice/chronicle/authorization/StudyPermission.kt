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
package com.openlattice.chronicle.authorization

/**
 * Defines granular permissions for study-level access control.
 * These permissions implement HIPAA's "minimum necessary" principle by allowing
 * fine-grained control over what actions users can perform on study data.
 *
 * @author uzaira0
 */
public enum class StudyPermission {
    /**
     * Allows reading study metadata and configuration.
     */
    READ_STUDY,

    /**
     * Allows viewing study participant data (de-identified or identified based on role).
     */
    READ_PARTICIPANT_DATA,

    /**
     * Allows modifying study configuration and settings.
     */
    MODIFY_STUDY,

    /**
     * Allows enrolling and managing participants in a study.
     */
    MANAGE_PARTICIPANTS,

    /**
     * Allows exporting study data in various formats.
     */
    EXPORT_DATA,

    /**
     * Allows managing questionnaires and surveys.
     */
    MANAGE_SURVEYS,

    /**
     * Allows viewing audit logs for the study.
     */
    VIEW_AUDIT_LOG,

    /**
     * Allows managing study permissions and access control.
     */
    MANAGE_PERMISSIONS,

    /**
     * Allows deleting study data (destructive operation).
     */
    DELETE_DATA,

    /**
     * Full administrative access to the study.
     */
    ADMIN
}
