package com.openlattice.chronicle.models

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException
import com.openlattice.chronicle.AbstractJacksonSerializationTest
import com.openlattice.chronicle.authorization.Principal
import com.openlattice.chronicle.authorization.PrincipalType
import com.openlattice.chronicle.authorization.SecurablePrincipal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.util.Optional
import java.util.UUID

class SecurablePrincipalJacksonSecurityTest {

    private val mapper = AbstractJacksonSerializationTest.objectMapper()

    @Test
    fun `securable principal uses explicit type name instead of class name typing`() {
        val principal = securablePrincipal()

        val json = mapper.writeValueAsString(principal)
        val restored = mapper.readValue(json, SecurablePrincipal::class.java)

        assertEquals(principal.id, restored.id)
        assertEquals(principal.principal, restored.principal)
        assertEquals(principal.title, restored.title)
        assertEquals("SecurablePrincipal", mapper.readTree(json).get("@class").asText())
    }

    @Test
    fun `legacy securable principal type id remains readable without arbitrary class loading`() {
        val json = mapper.writeValueAsString(securablePrincipal())
            .replace("\"SecurablePrincipal\"", "\"com.openlattice.chronicle.authorization.SecurablePrincipal\"")

        val restored = mapper.readValue(json, SecurablePrincipal::class.java)

        assertEquals(PrincipalType.USER, restored.principal.type)
    }

    @Test
    fun `unregistered securable principal type id is rejected`() {
        val json = mapper.writeValueAsString(securablePrincipal())
            .replace("\"SecurablePrincipal\"", "\"java.lang.Runtime\"")

        assertThrows(InvalidTypeIdException::class.java) {
            mapper.readValue(json, SecurablePrincipal::class.java)
        }
    }

    private fun securablePrincipal(): SecurablePrincipal {
        return SecurablePrincipal(
            Optional.of(UUID.randomUUID()),
            Principal(PrincipalType.USER, "user-1"),
            "User One",
            Optional.empty()
        )
    }
}
