package com.openlattice.chronicle.models

import com.openlattice.chronicle.AbstractJacksonSerializationTest
import com.openlattice.chronicle.authorization.AccessCheck
import com.openlattice.chronicle.authorization.Ace
import com.openlattice.chronicle.authorization.AceKey
import com.openlattice.chronicle.authorization.AceValue
import com.openlattice.chronicle.authorization.Acl
import com.openlattice.chronicle.authorization.AclData
import com.openlattice.chronicle.authorization.AclExplanation
import com.openlattice.chronicle.authorization.AclKey
import com.openlattice.chronicle.authorization.Action
import com.openlattice.chronicle.authorization.Authorization
import com.openlattice.chronicle.authorization.ChronicleStudyRole
import com.openlattice.chronicle.authorization.Permission
import com.openlattice.chronicle.authorization.Principal
import com.openlattice.chronicle.authorization.PrincipalType
import com.openlattice.chronicle.authorization.RoleAssignment
import com.openlattice.chronicle.authorization.ScopeType
import com.openlattice.chronicle.authorization.SecurableObjectType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import java.util.*

class AuthorizationModelsTest {

    // ===== Principal =====
    @Test fun testPrincipalConstruction() {
        val p = Principal(PrincipalType.USER, "test-user")
        assertEquals(PrincipalType.USER, p.type)
        assertEquals("test-user", p.id)
    }
    @Test fun testPrincipalEqualitySame() {
        assertEquals(Principal(PrincipalType.USER, "u1"), Principal(PrincipalType.USER, "u1"))
    }
    @Test fun testPrincipalInequalityDifferentType() {
        assertNotEquals(Principal(PrincipalType.USER, "u1"), Principal(PrincipalType.ROLE, "u1"))
    }
    @Test fun testPrincipalInequalityDifferentId() {
        assertNotEquals(Principal(PrincipalType.USER, "u1"), Principal(PrincipalType.USER, "u2"))
    }
    @Test fun testPrincipalHashCodeConsistency() {
        assertEquals(Principal(PrincipalType.USER, "u1").hashCode(), Principal(PrincipalType.USER, "u1").hashCode())
    }
    @Test fun testPrincipalCompareTo() {
        val p1 = Principal(PrincipalType.GROUP, "a")
        val p2 = Principal(PrincipalType.USER, "a")
        assertTrue(p1.compareTo(p2) < 0) // GROUP < USER by ordinal
    }
    @Test fun testPrincipalCompareToSameTypeById() {
        val p1 = Principal(PrincipalType.USER, "alpha")
        val p2 = Principal(PrincipalType.USER, "beta")
        assertTrue(p1.compareTo(p2) < 0)
    }
    @Test fun testPrincipalCompareToEqual() {
        val p1 = Principal(PrincipalType.USER, "u1")
        val p2 = Principal(PrincipalType.USER, "u1")
        assertEquals(0, p1.compareTo(p2))
    }
    @Test fun testPrincipalSelfEquality() {
        val p = Principal(PrincipalType.USER, "u1")
        assertEquals(p, p)
    }
    @Test fun testPrincipalNotEqualsNull() {
        assertNotEquals(Principal(PrincipalType.USER, "u1"), null)
    }

    // ===== AclKey =====
    @Test fun testAclKeyFromVarargs() {
        val id = UUID.randomUUID()
        val key = AclKey(id)
        assertEquals(1, key.size)
        assertEquals(id, key[0])
    }
    @Test fun testAclKeyFromList() {
        val id1 = UUID.randomUUID()
        val id2 = UUID.randomUUID()
        val key = AclKey(listOf(id1, id2))
        assertEquals(2, key.size)
        assertEquals(id1, key[0])
        assertEquals(id2, key[1])
    }
    @Test fun testAclKeyIndex() {
        val id = UUID(0L, 1L)
        val key = AclKey(id)
        assertNotNull(key.index)
        assertFalse(key.index.contains("-"))
    }
    @Test fun testAclKeyEquality() {
        val id = UUID.randomUUID()
        assertEquals(AclKey(id), AclKey(id))
    }
    @Test fun testAclKeyInequality() {
        assertNotEquals(AclKey(UUID.randomUUID()), AclKey(UUID.randomUUID()))
    }
    @Test fun testAclKeyHashCode() {
        val id = UUID.randomUUID()
        assertEquals(AclKey(id).hashCode(), AclKey(id).hashCode())
    }
    @Test fun testAclKeySelfEquality() {
        val key = AclKey(UUID.randomUUID())
        assertEquals(key, key)
    }

    // ===== AccessCheck =====
    @Test fun testAccessCheckConstruction() {
        val key = AclKey(UUID.randomUUID())
        val perms = EnumSet.of(Permission.READ)
        val check = AccessCheck(key, perms)
        assertEquals(key, check.aclKey)
        assertEquals(perms, check.permissions)
    }
    @Test fun testAccessCheckEquality() {
        val id = UUID.randomUUID()
        val check1 = AccessCheck(AclKey(id), EnumSet.of(Permission.READ))
        val check2 = AccessCheck(AclKey(id), EnumSet.of(Permission.READ))
        assertEquals(check1, check2)
    }
    @Test fun testAccessCheckInequalityDifferentPermissions() {
        val id = UUID.randomUUID()
        val check1 = AccessCheck(AclKey(id), EnumSet.of(Permission.READ))
        val check2 = AccessCheck(AclKey(id), EnumSet.of(Permission.WRITE))
        assertNotEquals(check1, check2)
    }

    // ===== Ace =====
    @Test fun testAceConstruction() {
        val principal = Principal(PrincipalType.USER, "u1")
        val ace = Ace(principal, setOf(Permission.READ, Permission.WRITE))
        assertEquals(principal, ace.principal)
        assertEquals(2, ace.permissions.size)
    }
    @Test fun testAceDefaultExpiration() {
        val ace = Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ))
        assertEquals(OffsetDateTime.MAX, ace.expirationDate)
    }
    @Test fun testAceCustomExpiration() {
        val expiry = OffsetDateTime.now().plusDays(30)
        val ace = Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ), expiry)
        assertEquals(expiry, ace.expirationDate)
    }
    @Test fun testAceEquality() {
        val p = Principal(PrincipalType.USER, "u1")
        val perms = setOf(Permission.READ)
        assertEquals(Ace(p, perms), Ace(p, perms))
    }
    @Test fun testAceInequalityDifferentPrincipal() {
        assertNotEquals(
            Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ)),
            Ace(Principal(PrincipalType.USER, "u2"), setOf(Permission.READ))
        )
    }
    @Test fun testAceHashCode() {
        val p = Principal(PrincipalType.USER, "u1")
        assertEquals(Ace(p, setOf(Permission.READ)).hashCode(), Ace(p, setOf(Permission.READ)).hashCode())
    }
    @Test fun testAceSelfEquality() {
        val ace = Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ))
        assertEquals(ace, ace)
    }

    // ===== AceKey =====
    @Test fun testAceKeyConstruction() {
        val aclKey = AclKey(UUID.randomUUID())
        val principal = Principal(PrincipalType.USER, "u1")
        val aceKey = AceKey(aclKey, principal)
        assertEquals(aclKey, aceKey.aclKey)
        assertEquals(principal, aceKey.principal)
    }
    @Test fun testAceKeyFromPrincipalAndUuids() {
        val id = UUID.randomUUID()
        val principal = Principal(PrincipalType.USER, "u1")
        val aceKey = AceKey(principal, id)
        assertEquals(principal, aceKey.principal)
        assertEquals(1, aceKey.aclKey.size)
    }
    @Test fun testAceKeyEquality() {
        val id = UUID.randomUUID()
        val p = Principal(PrincipalType.USER, "u1")
        assertEquals(AceKey(AclKey(id), p), AceKey(AclKey(id), p))
    }
    @Test fun testAceKeyHashCode() {
        val id = UUID.randomUUID()
        val p = Principal(PrincipalType.USER, "u1")
        assertEquals(AceKey(AclKey(id), p).hashCode(), AceKey(AclKey(id), p).hashCode())
    }

    // ===== AceValue =====
    @Test fun testAceValueConstruction() {
        val perms = EnumSet.of(Permission.READ, Permission.WRITE)
        val av = AceValue(perms, SecurableObjectType.Study)
        assertEquals(perms, av.permissions)
        assertEquals(SecurableObjectType.Study, av.securableObjectType)
    }
    @Test fun testAceValueDefaultExpiration() {
        val av = AceValue(EnumSet.of(Permission.READ), SecurableObjectType.Study)
        assertEquals(OffsetDateTime.MAX, av.expirationDate)
    }
    @Test fun testAceValueCustomExpiration() {
        val expiry = OffsetDateTime.now().plusDays(30)
        val av = AceValue(EnumSet.of(Permission.READ), SecurableObjectType.Study, expiry)
        assertEquals(expiry, av.expirationDate)
    }
    @Test fun testAceValueEquality() {
        val perms = EnumSet.of(Permission.READ)
        assertEquals(
            AceValue(perms, SecurableObjectType.Study),
            AceValue(perms, SecurableObjectType.Study)
        )
    }
    @Test fun testAceValueInequalityType() {
        val perms = EnumSet.of(Permission.READ)
        assertNotEquals(
            AceValue(perms, SecurableObjectType.Study),
            AceValue(perms, SecurableObjectType.Organization)
        )
    }
    @Test fun testAceValueHashCode() {
        val perms = EnumSet.of(Permission.READ)
        assertEquals(
            AceValue(perms, SecurableObjectType.Study).hashCode(),
            AceValue(perms, SecurableObjectType.Study).hashCode()
        )
    }
    @Test fun testAceValueMutableSetDelegation() {
        val perms = EnumSet.of(Permission.READ)
        val av = AceValue(perms, SecurableObjectType.Study)
        assertTrue(av.contains(Permission.READ))
        assertFalse(av.contains(Permission.WRITE))
    }

    // ===== Acl =====
    @Test fun testAclConstruction() {
        val key = AclKey(UUID.randomUUID())
        val ace = Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ))
        val acl = Acl(key, listOf(ace))
        assertEquals(key, acl.aclKey)
        assertEquals(1, acl.aces.count())
    }
    @Test fun testAclEquality() {
        val id = UUID.randomUUID()
        val key = AclKey(id)
        val ace = Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ))
        assertEquals(Acl(key, listOf(ace)), Acl(AclKey(id), listOf(ace)))
    }
    @Test fun testAclSelfEquality() {
        val acl = Acl(AclKey(UUID.randomUUID()), listOf())
        assertEquals(acl, acl)
    }
    @Test fun testAclHashCode() {
        val id = UUID.randomUUID()
        val key = AclKey(id)
        assertEquals(Acl(key, listOf()).hashCode(), Acl(AclKey(id), listOf()).hashCode())
    }

    // ===== AclData =====
    @Test fun testAclDataConstruction() {
        val key = AclKey(UUID.randomUUID())
        val ace = Ace(Principal(PrincipalType.USER, "u1"), setOf(Permission.READ))
        val acl = Acl(key, listOf(ace))
        val data = AclData(acl, Action.ADD)
        assertEquals(acl, data.acl)
        assertEquals(Action.ADD, data.action)
    }
    @Test fun testAclDataEquality() {
        val id = UUID.randomUUID()
        val acl = Acl(AclKey(id), listOf())
        assertEquals(AclData(acl, Action.ADD), AclData(acl, Action.ADD))
    }
    @Test fun testAclDataInequality() {
        val acl = Acl(AclKey(UUID.randomUUID()), listOf())
        assertNotEquals(AclData(acl, Action.ADD), AclData(acl, Action.REMOVE))
    }

    // ===== AclExplanation =====
    @Test fun testAclExplanationConstruction() {
        val p = Principal(PrincipalType.USER, "u1")
        val paths = listOf(listOf(p))
        val e = AclExplanation(p, paths)
        assertEquals(p, e.principal)
        assertEquals(1, e.paths.size)
    }
    @Test fun testAclExplanationEquality() {
        val p = Principal(PrincipalType.USER, "u1")
        val paths = listOf(listOf(p))
        assertEquals(AclExplanation(p, paths), AclExplanation(p, paths))
    }
    @Test fun testAclExplanationSelfEquality() {
        val e = AclExplanation(Principal(PrincipalType.USER, "u1"), listOf())
        assertEquals(e, e)
    }
    @Test fun testAclExplanationHashCode() {
        val p = Principal(PrincipalType.USER, "u1")
        assertEquals(AclExplanation(p, listOf()).hashCode(), AclExplanation(p, listOf()).hashCode())
    }

    // ===== Authorization =====
    @Test fun testAuthorizationConstruction() {
        val key = AclKey(UUID.randomUUID())
        val perms = mapOf(Permission.READ to true, Permission.WRITE to false)
        val auth = Authorization(key, perms)
        assertEquals(key, auth.aclKey)
        assertEquals(perms, auth.permissions)
    }
    @Test fun testAuthorizationToString() {
        val key = AclKey(UUID.randomUUID())
        val auth = Authorization(key, mapOf(Permission.READ to true))
        val s = auth.toString()
        assertTrue(s.contains("Authorization"))
        assertTrue(s.contains("READ"))
    }

    // ===== RoleAssignment =====
    @Test fun testRoleAssignmentConstruction() {
        val scopeId = UUID.randomUUID()
        val ra = RoleAssignment(
            principalId = "user1",
            scopeType = ScopeType.STUDY,
            scopeId = scopeId,
            role = ChronicleStudyRole.RESEARCHER
        )
        assertEquals("user1", ra.principalId)
        assertEquals(PrincipalType.USER, ra.principalType)
        assertEquals(ScopeType.STUDY, ra.scopeType)
        assertEquals(scopeId, ra.scopeId)
        assertEquals(ChronicleStudyRole.RESEARCHER, ra.role)
    }
    @Test fun testRoleAssignmentDefaultPrincipalType() {
        val ra = RoleAssignment("u", scopeType = ScopeType.STUDY, scopeId = UUID.randomUUID(), role = ChronicleStudyRole.ANALYST)
        assertEquals(PrincipalType.USER, ra.principalType)
    }
    @Test fun testRoleAssignmentDefaultAssignedByIsUnknown() {
        val ra = RoleAssignment("u", scopeType = ScopeType.STUDY, scopeId = UUID.randomUUID(), role = ChronicleStudyRole.ANALYST)
        assertNull(ra.assignedBy)
    }
    @Test fun testRoleAssignmentDefaultAssignedAtIsUnknown() {
        val ra = RoleAssignment("u", scopeType = ScopeType.STUDY, scopeId = UUID.randomUUID(), role = ChronicleStudyRole.ANALYST)
        assertNull(ra.assignedAt)
    }
    @Test fun testRoleAssignmentUnknownMetadataRoundTripsAsNull() {
        val ra = RoleAssignment(
            "u",
            scopeType = ScopeType.STUDY,
            scopeId = UUID.randomUUID(),
            role = ChronicleStudyRole.ANALYST,
        )
        val mapper = AbstractJacksonSerializationTest.objectMapper()

        val json = mapper.writeValueAsString(ra)
        val roundTrip = mapper.readValue(json, RoleAssignment::class.java)

        assertTrue(json.contains("\"assignedBy\":null"))
        assertTrue(json.contains("\"assignedAt\":null"))
        assertEquals(ra, roundTrip)
    }
}
