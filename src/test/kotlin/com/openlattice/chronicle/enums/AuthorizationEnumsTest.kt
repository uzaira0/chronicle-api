package com.openlattice.chronicle.enums

import com.openlattice.chronicle.apikey.ApiKeyScope
import com.openlattice.chronicle.authorization.Action
import com.openlattice.chronicle.authorization.ChronicleStudyRole
import com.openlattice.chronicle.authorization.Permission
import com.openlattice.chronicle.authorization.Principal
import com.openlattice.chronicle.authorization.PrincipalType
import com.openlattice.chronicle.authorization.Role
import com.openlattice.chronicle.authorization.ScopeType
import com.openlattice.chronicle.authorization.SecurableObjectType
import com.openlattice.chronicle.authorization.StudyPermission
import com.openlattice.chronicle.authorization.SystemRole
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class AuthorizationEnumsTest {

    // ===== Permission (6 values) =====
    @Test fun testPermissionValueCount() { assertEquals(6, Permission.values().size) }
    @Test fun testPermissionMaterialize() { assertEquals("MATERIALIZE", Permission.MATERIALIZE.name) }
    @Test fun testPermissionLink() { assertEquals("LINK", Permission.LINK.name) }
    @Test fun testPermissionRead() { assertEquals("READ", Permission.READ.name) }
    @Test fun testPermissionWrite() { assertEquals("WRITE", Permission.WRITE.name) }
    @Test fun testPermissionOwner() { assertEquals("OWNER", Permission.OWNER.name) }
    @Test fun testPermissionIntegrate() { assertEquals("INTEGRATE", Permission.INTEGRATE.name) }
    @Test fun testPermissionOrdinalMaterialize() { assertEquals(0, Permission.MATERIALIZE.ordinal) }
    @Test fun testPermissionOrdinalLink() { assertEquals(1, Permission.LINK.ordinal) }
    @Test fun testPermissionOrdinalRead() { assertEquals(2, Permission.READ.ordinal) }
    @Test fun testPermissionOrdinalWrite() { assertEquals(3, Permission.WRITE.ordinal) }
    @Test fun testPermissionOrdinalOwner() { assertEquals(4, Permission.OWNER.ordinal) }
    @Test fun testPermissionOrdinalIntegrate() { assertEquals(5, Permission.INTEGRATE.ordinal) }
    @Test fun testPermissionValueOfRead() { assertEquals(Permission.READ, Permission.valueOf("READ")) }
    @Test fun testPermissionValueOfWrite() { assertEquals(Permission.WRITE, Permission.valueOf("WRITE")) }
    @Test fun testPermissionValueOfInvalid() {
        try { Permission.valueOf("INVALID"); fail("Expected IllegalArgumentException") } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== PrincipalType (5 values) =====
    @Test fun testPrincipalTypeValueCount() { assertEquals(5, PrincipalType.values().size) }
    @Test fun testPrincipalTypeGroup() { assertEquals("GROUP", PrincipalType.GROUP.name) }
    @Test fun testPrincipalTypeOrganization() { assertEquals("ORGANIZATION", PrincipalType.ORGANIZATION.name) }
    @Test fun testPrincipalTypeRole() { assertEquals("ROLE", PrincipalType.ROLE.name) }
    @Test fun testPrincipalTypeUser() { assertEquals("USER", PrincipalType.USER.name) }
    @Test fun testPrincipalTypeApp() { assertEquals("APP", PrincipalType.APP.name) }
    @Test fun testPrincipalTypeOrdinalGroup() { assertEquals(0, PrincipalType.GROUP.ordinal) }
    @Test fun testPrincipalTypeOrdinalOrganization() { assertEquals(1, PrincipalType.ORGANIZATION.ordinal) }
    @Test fun testPrincipalTypeOrdinalRole() { assertEquals(2, PrincipalType.ROLE.ordinal) }
    @Test fun testPrincipalTypeOrdinalUser() { assertEquals(3, PrincipalType.USER.ordinal) }
    @Test fun testPrincipalTypeOrdinalApp() { assertEquals(4, PrincipalType.APP.ordinal) }
    @Test fun testPrincipalTypeValueOfUser() { assertEquals(PrincipalType.USER, PrincipalType.valueOf("USER")) }
    @Test fun testPrincipalTypeValueOfInvalid() {
        try {
            PrincipalType.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== SecurableObjectType (10 values) =====
    @Test fun testSecurableObjectTypeCount() { assertEquals(10, SecurableObjectType.values().size) }
    @Test fun testSOTOrganization() { assertEquals("Organization", SecurableObjectType.Organization.name) }
    @Test fun testSOTStudy() { assertEquals("Study", SecurableObjectType.Study.name) }
    @Test fun testSOTTimeUseDiary() { assertEquals("TimeUseDiary", SecurableObjectType.TimeUseDiary.name) }
    @Test fun testSOTCandidate() { assertEquals("Candidate", SecurableObjectType.Candidate.name) }
    @Test fun testSOTDatasource() { assertEquals("Datasource", SecurableObjectType.Datasource.name) }
    @Test fun testSOTModule() { assertEquals("Module", SecurableObjectType.Module.name) }
    @Test fun testSOTNotification() { assertEquals("Notification", SecurableObjectType.Notification.name) }
    @Test fun testSOTPrincipal() { assertEquals("Principal", SecurableObjectType.Principal.name) }
    @Test fun testSOTRole() { assertEquals("Role", SecurableObjectType.Role.name) }
    @Test fun testSOTUnknown() { assertEquals("Unknown", SecurableObjectType.Unknown.name) }
    @Test fun testSOTOrdinalOrganization() { assertEquals(0, SecurableObjectType.Organization.ordinal) }
    @Test fun testSOTOrdinalUnknown() { assertEquals(9, SecurableObjectType.Unknown.ordinal) }
    @Test fun testSOTValueOfStudy() { assertEquals(SecurableObjectType.Study, SecurableObjectType.valueOf("Study")) }
    @Test fun testSOTValueOfInvalid() {
        try {
            SecurableObjectType.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== Action (4 values) =====
    @Test fun testActionCount() { assertEquals(4, Action.values().size) }
    @Test fun testActionAdd() { assertEquals("ADD", Action.ADD.name) }
    @Test fun testActionRemove() { assertEquals("REMOVE", Action.REMOVE.name) }
    @Test fun testActionSet() { assertEquals("SET", Action.SET.name) }
    @Test fun testActionRequest() { assertEquals("REQUEST", Action.REQUEST.name) }
    @Test fun testActionOrdinalAdd() { assertEquals(0, Action.ADD.ordinal) }
    @Test fun testActionOrdinalRemove() { assertEquals(1, Action.REMOVE.ordinal) }
    @Test fun testActionOrdinalSet() { assertEquals(2, Action.SET.ordinal) }
    @Test fun testActionOrdinalRequest() { assertEquals(3, Action.REQUEST.ordinal) }
    @Test fun testActionValueOfAdd() { assertEquals(Action.ADD, Action.valueOf("ADD")) }
    @Test fun testActionValueOfInvalid() {
        try { Action.valueOf("INVALID"); fail("Expected IllegalArgumentException") } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== ApiKeyScope (3 values) =====
    @Test fun testApiKeyScopeCount() { assertEquals(3, ApiKeyScope.values().size) }
    @Test fun testApiKeyScopeReadOnly() { assertEquals("READ_ONLY", ApiKeyScope.READ_ONLY.name) }
    @Test fun testApiKeyScopeWrite() { assertEquals("WRITE", ApiKeyScope.WRITE.name) }
    @Test fun testApiKeyScopeAdmin() { assertEquals("ADMIN", ApiKeyScope.ADMIN.name) }
    @Test fun testApiKeyScopeOrdinalReadOnly() { assertEquals(0, ApiKeyScope.READ_ONLY.ordinal) }
    @Test fun testApiKeyScopeOrdinalWrite() { assertEquals(1, ApiKeyScope.WRITE.ordinal) }
    @Test fun testApiKeyScopeOrdinalAdmin() { assertEquals(2, ApiKeyScope.ADMIN.ordinal) }
    @Test fun testApiKeyScopeValueOfReadOnly() { assertEquals(ApiKeyScope.READ_ONLY, ApiKeyScope.valueOf("READ_ONLY")) }
    @Test fun testApiKeyScopeValueOfInvalid() {
        try {
            ApiKeyScope.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== StudyPermission (10 values) =====
    @Test fun testStudyPermissionCount() { assertEquals(10, StudyPermission.values().size) }
    @Test fun testStudyPermissionReadStudy() { assertEquals("READ_STUDY", StudyPermission.READ_STUDY.name) }
    @Test fun testStudyPermissionReadParticipantData() { assertEquals("READ_PARTICIPANT_DATA", StudyPermission.READ_PARTICIPANT_DATA.name) }
    @Test fun testStudyPermissionModifyStudy() { assertEquals("MODIFY_STUDY", StudyPermission.MODIFY_STUDY.name) }
    @Test fun testStudyPermissionManageParticipants() { assertEquals("MANAGE_PARTICIPANTS", StudyPermission.MANAGE_PARTICIPANTS.name) }
    @Test fun testStudyPermissionExportData() { assertEquals("EXPORT_DATA", StudyPermission.EXPORT_DATA.name) }
    @Test fun testStudyPermissionManageSurveys() { assertEquals("MANAGE_SURVEYS", StudyPermission.MANAGE_SURVEYS.name) }
    @Test fun testStudyPermissionViewAuditLog() { assertEquals("VIEW_AUDIT_LOG", StudyPermission.VIEW_AUDIT_LOG.name) }
    @Test fun testStudyPermissionManagePermissions() { assertEquals("MANAGE_PERMISSIONS", StudyPermission.MANAGE_PERMISSIONS.name) }
    @Test fun testStudyPermissionDeleteData() { assertEquals("DELETE_DATA", StudyPermission.DELETE_DATA.name) }
    @Test fun testStudyPermissionAdmin() { assertEquals("ADMIN", StudyPermission.ADMIN.name) }
    @Test fun testStudyPermissionValueOfAdmin() { assertEquals(StudyPermission.ADMIN, StudyPermission.valueOf("ADMIN")) }
    @Test fun testStudyPermissionValueOfInvalid() {
        try {
            StudyPermission.valueOf("INVALID"); fail("Expected IllegalArgumentException")
        } catch (expected: IllegalArgumentException) { /* expected */ }
    }

    // ===== ChronicleStudyRole (7 values) =====
    @Test fun testChronicleStudyRoleCount() { assertEquals(7, ChronicleStudyRole.values().size) }
    @Test fun testChronicleStudyRoleResearcher() { assertEquals("RESEARCHER", ChronicleStudyRole.RESEARCHER.name) }
    @Test fun testChronicleStudyRoleStudyAdmin() { assertEquals("STUDY_ADMIN", ChronicleStudyRole.STUDY_ADMIN.name) }
    @Test fun testChronicleStudyRolePI() { assertEquals("PI", ChronicleStudyRole.PI.name) }
    @Test fun testChronicleStudyRoleCoordinator() { assertEquals("COORDINATOR", ChronicleStudyRole.COORDINATOR.name) }
    @Test fun testChronicleStudyRoleAnalyst() { assertEquals("ANALYST", ChronicleStudyRole.ANALYST.name) }
    @Test fun testChronicleStudyRoleAuditor() { assertEquals("AUDITOR", ChronicleStudyRole.AUDITOR.name) }
    @Test fun testChronicleStudyRoleSystemAdmin() { assertEquals("SYSTEM_ADMIN", ChronicleStudyRole.SYSTEM_ADMIN.name) }

    @Test fun testResearcherHasReadStudy() { assertTrue(ChronicleStudyRole.RESEARCHER.hasPermission(StudyPermission.READ_STUDY)) }
    @Test fun testResearcherHasReadParticipantData() {
        assertTrue(ChronicleStudyRole.RESEARCHER.hasPermission(StudyPermission.READ_PARTICIPANT_DATA))
    }
    @Test fun testResearcherHasExportData() { assertTrue(ChronicleStudyRole.RESEARCHER.hasPermission(StudyPermission.EXPORT_DATA)) }
    @Test fun testResearcherLacksModifyStudy() { assertFalse(ChronicleStudyRole.RESEARCHER.hasPermission(StudyPermission.MODIFY_STUDY)) }
    @Test fun testResearcherLacksDeleteData() { assertFalse(ChronicleStudyRole.RESEARCHER.hasPermission(StudyPermission.DELETE_DATA)) }
    @Test fun testResearcherPermissionCount() { assertEquals(3, ChronicleStudyRole.RESEARCHER.permissions.size) }

    @Test fun testStudyAdminHasDeleteData() { assertTrue(ChronicleStudyRole.STUDY_ADMIN.hasPermission(StudyPermission.DELETE_DATA)) }
    @Test fun testStudyAdminHasManagePermissions() { assertTrue(ChronicleStudyRole.STUDY_ADMIN.hasPermission(StudyPermission.MANAGE_PERMISSIONS)) }
    @Test fun testStudyAdminPermissionCount() { assertEquals(9, ChronicleStudyRole.STUDY_ADMIN.permissions.size) }
    @Test fun testStudyAdminLacksAdmin() { assertFalse(ChronicleStudyRole.STUDY_ADMIN.hasPermission(StudyPermission.ADMIN)) }

    @Test fun testCoordinatorPermissionCount() { assertEquals(6, ChronicleStudyRole.COORDINATOR.permissions.size) }
    @Test fun testCoordinatorLacksDeleteData() { assertFalse(ChronicleStudyRole.COORDINATOR.hasPermission(StudyPermission.DELETE_DATA)) }
    @Test fun testCoordinatorLacksManagePermissions() {
        assertFalse(ChronicleStudyRole.COORDINATOR.hasPermission(StudyPermission.MANAGE_PERMISSIONS))
    }

    @Test fun testAuditorHasReadStudy() { assertTrue(ChronicleStudyRole.AUDITOR.hasPermission(StudyPermission.READ_STUDY)) }
    @Test fun testAuditorHasViewAuditLog() { assertTrue(ChronicleStudyRole.AUDITOR.hasPermission(StudyPermission.VIEW_AUDIT_LOG)) }
    @Test fun testAuditorPermissionCount() { assertEquals(2, ChronicleStudyRole.AUDITOR.permissions.size) }

    @Test fun testSystemAdminHasAllPermissions() { assertEquals(StudyPermission.values().size, ChronicleStudyRole.SYSTEM_ADMIN.permissions.size) }
    @Test fun testSystemAdminHasAdmin() { assertTrue(ChronicleStudyRole.SYSTEM_ADMIN.hasPermission(StudyPermission.ADMIN)) }

    @Test fun testHasAllPermissionsTrue() {
        assertTrue(ChronicleStudyRole.SYSTEM_ADMIN.hasAllPermissions(setOf(StudyPermission.READ_STUDY, StudyPermission.ADMIN)))
    }
    @Test fun testHasAllPermissionsFalse() {
        assertFalse(ChronicleStudyRole.RESEARCHER.hasAllPermissions(setOf(StudyPermission.READ_STUDY, StudyPermission.ADMIN)))
    }
    @Test fun testHasAnyPermissionTrue() {
        assertTrue(ChronicleStudyRole.RESEARCHER.hasAnyPermission(setOf(StudyPermission.READ_STUDY, StudyPermission.ADMIN)))
    }
    @Test fun testHasAnyPermissionFalse() {
        assertFalse(ChronicleStudyRole.AUDITOR.hasAnyPermission(setOf(StudyPermission.DELETE_DATA, StudyPermission.ADMIN)))
    }

    @Test fun testMinimumRoleForReadStudy() {
        assertEquals(ChronicleStudyRole.RESEARCHER, ChronicleStudyRole.minimumRoleFor(StudyPermission.READ_STUDY))
    }
    @Test fun testMinimumRoleForAdmin() { assertEquals(ChronicleStudyRole.SYSTEM_ADMIN, ChronicleStudyRole.minimumRoleFor(StudyPermission.ADMIN)) }

    // ===== SystemRole (4 values) =====
    @Test fun testSystemRoleCount() { assertEquals(4, SystemRole.values().size) }
    @Test fun testSystemRoleAdmin() { assertEquals("ADMIN", SystemRole.ADMIN.name) }
    @Test fun testSystemRoleUser() { assertEquals("USER", SystemRole.USER.name) }
    @Test fun testSystemRoleAnonymousUser() { assertEquals("ANONYMOUS_USER", SystemRole.ANONYMOUS_USER.name) }
    @Test fun testSystemRoleAuthenticatedUser() { assertEquals("AUTHENTICATED_USER", SystemRole.AUTHENTICATED_USER.name) }
    @Test fun testSystemRoleAdminPrincipalId() { assertEquals("admin", SystemRole.ADMIN.getName()) }
    @Test fun testSystemRoleUserPrincipalId() { assertEquals("user", SystemRole.USER.getName()) }
    @Test fun testSystemRoleAnonymousUserPrincipalId() { assertEquals("AnonymousUser", SystemRole.ANONYMOUS_USER.getName()) }
    @Test fun testSystemRoleAuthenticatedUserPrincipalId() { assertEquals("AuthenticatedUser", SystemRole.AUTHENTICATED_USER.getName()) }
    @Test fun testSystemRoleAdminPrincipalType() { assertEquals(PrincipalType.ROLE, SystemRole.ADMIN.principal.type) }
    @Test fun testSystemRoleAdminRoleCompanion() { assertEquals(SystemRole.ADMIN.principal, SystemRole.adminRole) }
    @Test fun testSystemRoleContainsAdmin() { assertTrue(SystemRole.contains("admin")) }
    @Test fun testSystemRoleContainsUser() { assertTrue(SystemRole.contains("user")) }
    @Test fun testSystemRoleNotContainsInvalid() { assertFalse(SystemRole.contains("invalid")) }
    @Test fun testSystemRoleValuesAsArray() { assertEquals(4, SystemRole.valuesAsArray().size) }

    // ===== ScopeType (2 values) =====
    @Test fun testScopeTypeCount() { assertEquals(2, ScopeType.values().size) }
    @Test fun testScopeTypeStudy() { assertEquals("STUDY", ScopeType.STUDY.name) }
    @Test fun testScopeTypeOrganization() { assertEquals("ORGANIZATION", ScopeType.ORGANIZATION.name) }
    @Test fun testScopeTypeOrdinalStudy() { assertEquals(0, ScopeType.STUDY.ordinal) }
    @Test fun testScopeTypeOrdinalOrganization() { assertEquals(1, ScopeType.ORGANIZATION.ordinal) }
    @Test fun testScopeTypeValueOfStudy() { assertEquals(ScopeType.STUDY, ScopeType.valueOf("STUDY")) }
    @Test fun testScopeTypeValueOfInvalid() {
        try { ScopeType.valueOf("INVALID"); fail("Expected IllegalArgumentException") } catch (expected: IllegalArgumentException) { /* expected */ }
    }
}
