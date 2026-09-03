package com.openlattice.chronicle.models

import com.openlattice.chronicle.webhooks.WebhookCreateRequest
import com.openlattice.chronicle.webhooks.WebhookDeliveryInfo
import com.openlattice.chronicle.webhooks.WebhookEventType
import com.openlattice.chronicle.webhooks.WebhookRegistration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

class WebhookModelsTest {

    // ===== WebhookCreateRequest =====
    @Test fun testWebhookCreateRequestConstruction() {
        val r = WebhookCreateRequest(url = "https://example.com/hook")
        assertEquals("https://example.com/hook", r.url)
    }
    @Test fun testWebhookCreateRequestDefaultSecret() { assertEquals("", WebhookCreateRequest(url = "https://x.com").secret) }
    @Test fun testWebhookCreateRequestDefaultEventTypes() { assertTrue(WebhookCreateRequest(url = "https://x.com").eventTypes.isEmpty()) }
    @Test fun testWebhookCreateRequestDefaultDescription() { assertEquals("", WebhookCreateRequest(url = "https://x.com").description) }
    @Test fun testWebhookCreateRequestCustomEventTypes() {
        val r = WebhookCreateRequest(url = "https://x.com", eventTypes = setOf(WebhookEventType.DATA_SUBMITTED, WebhookEventType.EXPORT_COMPLETED))
        assertEquals(2, r.eventTypes.size)
        assertTrue(r.eventTypes.contains(WebhookEventType.DATA_SUBMITTED))
    }
    @Test fun testWebhookCreateRequestCustomSecret() {
        val r = WebhookCreateRequest(url = "https://x.com", secret = "s3cr3t")
        assertEquals("s3cr3t", r.secret)
    }
    @Test fun testWebhookCreateRequestCustomDescription() {
        val r = WebhookCreateRequest(url = "https://x.com", description = "My webhook")
        assertEquals("My webhook", r.description)
    }
    @Test fun testWebhookCreateRequestEquality() {
        assertEquals(
            WebhookCreateRequest(url = "https://x.com"),
            WebhookCreateRequest(url = "https://x.com")
        )
    }
    @Test fun testWebhookCreateRequestInequality() {
        assertNotEquals(
            WebhookCreateRequest(url = "https://a.com"),
            WebhookCreateRequest(url = "https://b.com")
        )
    }
    @Test fun testWebhookCreateRequestToString() { assertNotNull(WebhookCreateRequest(url = "https://x.com").toString()) }

    // ===== WebhookRegistration =====
    @Test fun testWebhookRegistrationDefaults() {
        val r = WebhookRegistration()
        assertNotNull(r.webhookId)
        assertEquals(UUID(0, 0), r.studyId)
        assertEquals("", r.url)
        assertEquals("", r.secret)
        assertTrue(r.eventTypes.isEmpty())
        assertTrue(r.enabled)
        assertEquals("", r.description)
        assertNull(r.createdAt)
    }
    @Test fun testWebhookRegistrationCustom() {
        val id = UUID.randomUUID()
        val studyId = UUID.randomUUID()
        val now = OffsetDateTime.now()
        val r = WebhookRegistration(
            webhookId = id,
            studyId = studyId,
            url = "https://x.com",
            secret = "sec",
            eventTypes = setOf(WebhookEventType.PARTICIPANT_ENROLLED),
            enabled = false,
            description = "desc",
            createdAt = now
        )
        assertEquals(id, r.webhookId)
        assertEquals(studyId, r.studyId)
        assertEquals("https://x.com", r.url)
        assertEquals("sec", r.secret)
        assertEquals(1, r.eventTypes.size)
        assertFalse(r.enabled)
        assertEquals("desc", r.description)
        assertEquals(now, r.createdAt)
    }
    @Test fun testWebhookRegistrationToString() { assertNotNull(WebhookRegistration().toString()) }

    // ===== WebhookDeliveryInfo =====
    @Test fun testWebhookDeliveryInfoConstruction() {
        val delivId = UUID.randomUUID()
        val hookId = UUID.randomUUID()
        val now = OffsetDateTime.now()
        val info = WebhookDeliveryInfo(
            deliveryId = delivId,
            webhookId = hookId,
            eventType = WebhookEventType.DATA_SUBMITTED,
            status = 200,
            attemptCount = 1,
            createdAt = now
        )
        assertEquals(delivId, info.deliveryId)
        assertEquals(hookId, info.webhookId)
        assertEquals(WebhookEventType.DATA_SUBMITTED, info.eventType)
        assertEquals(200, info.status)
        assertEquals(1, info.attemptCount)
        assertEquals(now, info.createdAt)
    }
    @Test fun testWebhookDeliveryInfoDefaultLastAttemptAt() {
        val info = WebhookDeliveryInfo(UUID.randomUUID(), UUID.randomUUID(), WebhookEventType.DATA_SUBMITTED, 200, 1, OffsetDateTime.now())
        assertNull(info.lastAttemptAt)
    }
    @Test fun testWebhookDeliveryInfoCustomLastAttemptAt() {
        val now = OffsetDateTime.now()
        val info = WebhookDeliveryInfo(UUID.randomUUID(), UUID.randomUUID(), WebhookEventType.DATA_SUBMITTED, 500, 3, now, lastAttemptAt = now)
        assertEquals(now, info.lastAttemptAt)
    }
    @Test fun testWebhookDeliveryInfoToString() {
        val info = WebhookDeliveryInfo(UUID.randomUUID(), UUID.randomUUID(), WebhookEventType.DATA_SUBMITTED, 200, 1, OffsetDateTime.now())
        assertNotNull(info.toString())
    }
}
