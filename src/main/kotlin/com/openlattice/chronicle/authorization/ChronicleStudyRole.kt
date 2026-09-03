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

import java.util.EnumSet

/**
 * Defines roles for study-level access control in Chronicle.
 * Each role has a specific set of permissions that implement HIPAA's "minimum necessary" principle.
 *
 * Note: Participants do NOT have system accounts. They submit data via mobile API with app key
 * authentication only and never read data back through the system.
 *
 * @author uzaira0
 */
public enum class ChronicleStudyRole(
    /**
     * The set of permissions granted to this role.
     */
    public val permissions: EnumSet<StudyPermission>
) {
    /**
     * Researcher role - Can view study data for studies they're members of.
     * Limited to read-only access on participant data.
     */
    RESEARCHER(
        EnumSet.of(
            StudyPermission.READ_STUDY,
            StudyPermission.READ_PARTICIPANT_DATA,
            StudyPermission.EXPORT_DATA
        )
    ),

    /**
     * Study Administrator role - Can manage studies they own.
     * Full control over their studies including participant management.
     */
    STUDY_ADMIN(
        EnumSet.of(
            StudyPermission.READ_STUDY,
            StudyPermission.READ_PARTICIPANT_DATA,
            StudyPermission.MODIFY_STUDY,
            StudyPermission.MANAGE_PARTICIPANTS,
            StudyPermission.EXPORT_DATA,
            StudyPermission.MANAGE_SURVEYS,
            StudyPermission.VIEW_AUDIT_LOG,
            StudyPermission.MANAGE_PERMISSIONS,
            StudyPermission.DELETE_DATA
        )
    ),

    /**
     * Principal Investigator - Full permissions on studies they own (replaces STUDY_ADMIN for research contexts).
     */
    PI(
        EnumSet.of(
            StudyPermission.READ_STUDY,
            StudyPermission.READ_PARTICIPANT_DATA,
            StudyPermission.MODIFY_STUDY,
            StudyPermission.MANAGE_PARTICIPANTS,
            StudyPermission.EXPORT_DATA,
            StudyPermission.MANAGE_SURVEYS,
            StudyPermission.VIEW_AUDIT_LOG,
            StudyPermission.MANAGE_PERMISSIONS,
            StudyPermission.DELETE_DATA
        )
    ),

    /**
     * Coordinator - Manages participants, exports data, manages surveys but cannot delete data or manage permissions.
     */
    COORDINATOR(
        EnumSet.of(
            StudyPermission.READ_STUDY,
            StudyPermission.READ_PARTICIPANT_DATA,
            StudyPermission.MODIFY_STUDY,
            StudyPermission.MANAGE_PARTICIPANTS,
            StudyPermission.EXPORT_DATA,
            StudyPermission.MANAGE_SURVEYS
        )
    ),

    /**
     * Analyst - Read-only data access with export capability.
     */
    ANALYST(
        EnumSet.of(
            StudyPermission.READ_STUDY,
            StudyPermission.READ_PARTICIPANT_DATA,
            StudyPermission.EXPORT_DATA
        )
    ),

    /**
     * Auditor - Read-only access to study metadata and audit logs.
     */
    AUDITOR(
        EnumSet.of(
            StudyPermission.READ_STUDY,
            StudyPermission.VIEW_AUDIT_LOG
        )
    ),

    /**
     * System Administrator role - Full system access with audit trail.
     * Has all permissions across all studies.
     */
    SYSTEM_ADMIN(
        EnumSet.allOf(StudyPermission::class.java)
    );

    /**
     * Checks if this role has the specified permission.
     *
     * @param permission The permission to check.
     * @return true if the role has the permission, false otherwise.
     */
    public fun hasPermission(permission: StudyPermission): Boolean {
        return permissions.contains(permission)
    }

    /**
     * Checks if this role has all of the specified permissions.
     *
     * @param requiredPermissions The permissions to check.
     * @return true if the role has all permissions, false otherwise.
     */
    public fun hasAllPermissions(requiredPermissions: Set<StudyPermission>): Boolean {
        return permissions.containsAll(requiredPermissions)
    }

    /**
     * Checks if this role has any of the specified permissions.
     *
     * @param requiredPermissions The permissions to check.
     * @return true if the role has at least one of the permissions, false otherwise.
     */
    public fun hasAnyPermission(requiredPermissions: Set<StudyPermission>): Boolean {
        return requiredPermissions.any { permissions.contains(it) }
    }

    public companion object {
        /**
         * Returns the minimum role required for a given permission.
         *
         * @param permission The permission to check.
         * @return The minimum role that has this permission, or null if no role has it.
         */
        public fun minimumRoleFor(permission: StudyPermission): ChronicleStudyRole? {
            return values().firstOrNull { it.hasPermission(permission) }
        }
    }
}
