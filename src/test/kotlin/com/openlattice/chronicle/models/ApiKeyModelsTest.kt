package com.openlattice.chronicle.models

import com.openlattice.chronicle.apikey.ApiKeyCreateRequest
import com.openlattice.chronicle.apikey.ApiKeyCreateResponse
import com.openlattice.chronicle.apikey.ApiKeyInfo
import com.openlattice.chronicle.apikey.ApiKeyScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

class ApiKeyModelsTest {

    // ===== ApiKeyCreateRequest =====
    @Test fun testApiKeyCreateRequestDefault() { assertNotNull(ApiKeyCreateRequest()) }
    @Test fun testApiKeyCreateRequestDefaultName() { assertEquals("", ApiKeyCreateRequest().name) }
    @Test fun testApiKeyCreateRequestDefaultScope() { assertEquals(ApiKeyScope.READ_ONLY, ApiKeyCreateRequest().scope) }
    @Test fun testApiKeyCreateRequestDefaultExpires() { assertEquals(90, ApiKeyCreateRequest().expiresInDays) }

    @Test fun testApiKeyCreateRequestCustomName() {
        val r = ApiKeyCreateRequest(name = "test-key")
        assertEquals("test-key", r.name)
    }
    @Test fun testApiKeyCreateRequestCustomScope() {
        val r = ApiKeyCreateRequest(scope = ApiKeyScope.ADMIN)
        assertEquals(ApiKeyScope.ADMIN, r.scope)
    }
    @Test fun testApiKeyCreateRequestCustomExpires() {
        val r = ApiKeyCreateRequest(expiresInDays = 365)
        assertEquals(365, r.expiresInDays)
    }

    @Test fun testApiKeyCreateRequestEquality() {
        assertEquals(
            ApiKeyCreateRequest(name = "k", scope = ApiKeyScope.WRITE, expiresInDays = 30),
            ApiKeyCreateRequest(name = "k", scope = ApiKeyScope.WRITE, expiresInDays = 30)
        )
    }
    @Test fun testApiKeyCreateRequestInequality() {
        assertNotEquals(
            ApiKeyCreateRequest(name = "k1"),
            ApiKeyCreateRequest(name = "k2")
        )
    }
    @Test fun testApiKeyCreateRequestHashCode() {
        assertEquals(
            ApiKeyCreateRequest(name = "k").hashCode(),
            ApiKeyCreateRequest(name = "k").hashCode()
        )
    }
    @Test fun testApiKeyCreateRequestCopy() {
        val r = ApiKeyCreateRequest(name = "original")
        val c = r.copy(name = "modified")
        assertEquals("modified", c.name)
        assertEquals(r.scope, c.scope)
    }
    @Test fun testApiKeyCreateRequestToString() { assertNotNull(ApiKeyCreateRequest().toString()) }

    // ===== ApiKeyInfo =====
    @Test fun testApiKeyInfoConstruction() {
        val keyId = UUID.randomUUID()
        val studyId = UUID.randomUUID()
        val now = OffsetDateTime.now()
        val info = ApiKeyInfo(keyId, studyId, "chr_", "my-key", ApiKeyScope.READ_ONLY, now, now.plusDays(90))
        assertEquals(keyId, info.keyId)
        assertEquals(studyId, info.studyId)
        assertEquals("chr_", info.prefix)
        assertEquals("my-key", info.name)
        assertEquals(ApiKeyScope.READ_ONLY, info.scope)
        assertEquals(now, info.createdAt)
    }
    @Test fun testApiKeyInfoDefaultLastUsedAt() {
        val info = ApiKeyInfo(
            UUID.randomUUID(), UUID.randomUUID(), "chr_", "k", ApiKeyScope.READ_ONLY,
            OffsetDateTime.now(), OffsetDateTime.now().plusDays(90)
        )
        assertNull(info.lastUsedAt)
    }
    @Test fun testApiKeyInfoDefaultUsageCount() {
        val info = ApiKeyInfo(
            UUID.randomUUID(), UUID.randomUUID(), "chr_", "k", ApiKeyScope.READ_ONLY,
            OffsetDateTime.now(), OffsetDateTime.now().plusDays(90)
        )
        assertEquals(0L, info.usageCount)
    }
    @Test fun testApiKeyInfoCustomUsageCount() {
        val info = ApiKeyInfo(
            UUID.randomUUID(), UUID.randomUUID(), "chr_", "k", ApiKeyScope.READ_ONLY,
            OffsetDateTime.now(), OffsetDateTime.now().plusDays(90), usageCount = 42
        )
        assertEquals(42L, info.usageCount)
    }
    @Test fun testApiKeyInfoToString() {
        val info = ApiKeyInfo(
            UUID.randomUUID(), UUID.randomUUID(), "chr_", "k", ApiKeyScope.READ_ONLY,
            OffsetDateTime.now(), OffsetDateTime.now().plusDays(90)
        )
        assertNotNull(info.toString())
    }

    // ===== ApiKeyCreateResponse =====
    @Test fun testApiKeyCreateResponseConstruction() {
        val keyId = UUID.randomUUID()
        val info = ApiKeyInfo(
            keyId, UUID.randomUUID(), "chr_", "k", ApiKeyScope.READ_ONLY,
            OffsetDateTime.now(), OffsetDateTime.now().plusDays(90)
        )
        val resp = ApiKeyCreateResponse(keyId, "chr_abcdef123456", info)
        assertEquals(keyId, resp.keyId)
        assertEquals("chr_abcdef123456", resp.rawKey)
        assertEquals(info, resp.info)
    }
    @Test fun testApiKeyCreateResponseToString() {
        val info = ApiKeyInfo(
            UUID.randomUUID(), UUID.randomUUID(), "chr_", "k", ApiKeyScope.READ_ONLY,
            OffsetDateTime.now(), OffsetDateTime.now().plusDays(90)
        )
        val resp = ApiKeyCreateResponse(UUID.randomUUID(), "raw", info)
        assertNotNull(resp.toString())
    }
}
