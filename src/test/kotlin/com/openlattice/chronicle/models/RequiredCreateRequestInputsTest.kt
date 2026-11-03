package com.openlattice.chronicle.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.openlattice.chronicle.apikey.ApiKeyCreateRequest
import com.openlattice.chronicle.webhooks.WebhookCreateRequest
import jakarta.validation.Validation
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class RequiredCreateRequestInputsTest {
    private val mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    private val validator = Validation.byDefaultProvider().configure()
        .messageInterpolator(ParameterMessageInterpolator()).buildValidatorFactory().validator

    @Test fun aMissingApiKeyNameIsRejectedByTheReader() {
        assertThrows(MismatchedInputException::class.java) { mapper.readValue("{}", ApiKeyCreateRequest::class.java) }
    }

    @Test fun aMissingWebhookSecretIsRejectedByTheReader() {
        val body = """{"url":"https://example.org/callback","eventTypes":["DATA_SUBMITTED"]}"""
        assertThrows(MismatchedInputException::class.java) { mapper.readValue(body, WebhookCreateRequest::class.java) }
    }

    @Test fun missingWebhookSubscriptionsAreRejectedByTheReader() {
        val body = mapper.writeValueAsString(mapOf("url" to "https://example.org/callback", "secret" to "s".repeat(32)))
        assertThrows(MismatchedInputException::class.java) { mapper.readValue(body, WebhookCreateRequest::class.java) }
    }

    @Test fun explicitEmptyValuesStillReachBeanValidation() {
        assertTrue(validator.validate(ApiKeyCreateRequest(name = "")).any { it.propertyPath.toString() == "name" })
        val invalid = WebhookCreateRequest(url = "https://example.org/callback", secret = "", eventTypes = emptySet())
        assertEquals(setOf("secret", "eventTypes"), validator.validate(invalid).map { it.propertyPath.toString() }.toSet())
    }
}
